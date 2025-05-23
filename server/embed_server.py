from flask import Flask, request, jsonify               # Cung cap cac cong cu can thiet de xay dung API web
from sentence_transformers import SentenceTransformer   # Cho phep su dung mo hinh SentenceTransformer de chuyen doi van ban thanh vector

app = Flask(__name__)                                   # Khoi tao ung dung web Flask de xu ly cac yeu cau HTTP 
model = SentenceTransformer('all-MiniLM-L6-v2')         # Tao mot instance cua mo hinh all-MiniLM-L6-v2 - SentenceTransformer 
                                                        # Chuyen doi van ban thanh vector 384 chieu, phu hop cho so sanh ngu nghia, tim kiem va phan loai
@app.route('/embed', methods=['POST'])                  # Tao mot endpoint API de client gui danh sach cac van ban can tao embedding

def embed():
    data = request.json                                 # Lay du lieu JSON tu yeu cau POST cua client va gan vao bien data. 
                                                        # Data la mot dictionary Python chua du lieu tu client
    sentences = data.get("sentences", [])               # Lay danh sach cac cau van ban tu JSON de xu ly
                                                        # Lay gia tri cua key sentences. Neu khong ton tai, tra ve danh sach rong
    embeddings = model.encode(sentences).tolist()       # Chuyen danh sach cau sang thanh cac vector embedding 
                                                        # Ket qua la mot mang NumPy chua cac vector
                                                        # Chuyen mang NumPy thanh danh sach Python thong thuong de de dang chuyen thanh JSON

    return jsonify(embeddings)                          # Chuyen doi danh sach thanh dinh dang JSON va tra ve nhu phan hoi HTTP cho client

if __name__ == '__main__':                              # Chay ung dung Flask nhu moi server web, cho phep client gui yeu cau den http://localhost:5000/embed
    app.run(host='0.0.0.0', port=5000)
