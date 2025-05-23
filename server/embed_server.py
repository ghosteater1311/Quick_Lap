from flask import Flask, request, jsonify
from sentence_transformers import SentenceTransformer

app = Flask(__name__)
model = SentenceTransformer('all-MiniLM-L6-v2')  # Nhẹ và nhanh

@app.route('/embed', methods=['POST'])
def embed():
    data = request.json
    sentences = data.get("sentences", [])
    embeddings = model.encode(sentences).tolist()
    return jsonify(embeddings)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
