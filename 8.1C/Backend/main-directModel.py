from flask import Flask, request, Response
from transformers import AutoTokenizer, AutoModelForCausalLM, BitsAndBytesConfig
import torch
import argparse

app = Flask(__name__)
model = None
tokenizer = None

MODEL = "meta-llama/Llama-3.2-1B"
MODEL = "google/gemma-3-1b-it"


def prepareLlamaBot():
    global model, tokenizer
    print(f"Loading {MODEL} model... This may take a while.")

    # print("Loading Llama-3.2-1B model with 4-bit quantization... This may take a while.")

    # Configure 4-bit quantization
    # quantization_config = BitsAndBytesConfig(
    #     load_in_4bit=True,
    #     bnb_4bit_compute_dtype=torch.float16,
    #     bnb_4bit_quant_type="nf4",
    #     bnb_4bit_use_double_quant=False
    # )

    # Load tokenizer
    tokenizer = AutoTokenizer.from_pretrained(MODEL)
    tokenizer.pad_token = tokenizer.eos_token if tokenizer.pad_token is None else tokenizer.pad_token

    # Load model with quantization
    model = AutoModelForCausalLM.from_pretrained(
        MODEL,
        # device_map="auto",
        # # quantization_config=quantization_config,
        # torch_dtype=torch.float16 if torch.cuda.is_available() else torch.float32
    )

    print("Model and tokenizer loaded successfully.")


@app.route('/')
def index():
    return "Welcome to the Llama Chatbot API!"


from flask import jsonify, request, Response

@app.route('/chat', methods=['POST'])
def chat():
    # 1. Parse JSON
    data = request.get_json(force=True)

    # 2. Extract just the user’s message
    user_message = data.get("message", "").strip()
    if not user_message:
        return Response("Error: message cannot be empty", status=400, mimetype='text/plain')

    # 3. Build a chat-style prompt
    system_prompt = "You are a helpful, friendly assistant."
    prompt = f"{system_prompt}\nUser: {user_message}\nAssistant:"

    # 4. Tokenize & generate
    inputs = tokenizer(prompt, return_tensors="pt", truncation=True, max_length=512, padding=True)
    if torch.cuda.is_available():
        inputs = {k: v.cuda() for k, v in inputs.items()}
    with torch.no_grad():
        outputs = model.generate(
            **inputs,
            max_new_tokens=100,
            do_sample=True,
            top_p=0.85,
            temperature=0.6,
            pad_token_id=tokenizer.eos_token_id,
            no_repeat_ngram_size=2
        )
    full_reply = tokenizer.decode(outputs[0], skip_special_tokens=True)

    # 5. Strip the prompt off the front
    reply = full_reply[len(prompt):].strip()

    # 6. Log & return
    print("Raw Model Output:", full_reply)
    print("Generated Response:", reply)
    return jsonify({"reply": reply})


if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('--port', type=int, default=5001, help='Specify the port number')
    args = parser.parse_args()

    port_num = args.port
    prepareLlamaBot()
    print(f"App running on port {port_num}")
    app.run(host='0.0.0.0', port=port_num)
