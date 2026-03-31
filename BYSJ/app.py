import sys
from flask import Flask, request, jsonify
from PIL import Image
import io
import torch
from pathlib import Path
from torchvision import transforms
from torchvision.models import resnet50
import torch.nn as nn

app = Flask(__name__)
BASE_DIR = Path(__file__).resolve().parent
MODEL_PATH = BASE_DIR / "model_Resnet50.pth"


# 加载 ResNet 模型
def load_model():
    model = resnet50(weights=None)  # 确保没有加载预训练权重
    model.fc = nn.Linear(model.fc.in_features, 2)  # 调整最后的全连接层以适应您的输出需求
    if not MODEL_PATH.exists():
        raise FileNotFoundError(f"模型文件不存在: {MODEL_PATH}")
    model.load_state_dict(torch.load(str(MODEL_PATH), map_location=torch.device('cpu')))  # CPU 环境兼容加载
    model.eval()
    return model


model = load_model()  # 在全局加载模型，避免每次调用时重新加载

# 图像预处理
transform = transforms.Compose([
    transforms.Resize(256),
    transforms.CenterCrop(224),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
])


# 预测函数
def predict(image_bytes):
    image = Image.open(io.BytesIO(image_bytes))
    image_tensor = transform(image).unsqueeze(0)  # 转为张量并增加批次维度

    with torch.no_grad():  # 关闭梯度计算
        outputs = model(image_tensor)
        probabilities = torch.softmax(outputs, dim=1)
        _, predicted = torch.max(outputs, 1)
        result = '疑似阳性' if predicted.item() == 1 else '阴性'
        confidence = round(probabilities[0][predicted.item()].item() * 100, 2)

    return result, confidence


@app.route('/predict', methods=['POST'])
def handle_request():
    if 'file' not in request.files:
        return jsonify({'error': 'No file provided'}), 400

    file = request.files['file']
    img_bytes = file.read()
    result, confidence = predict(img_bytes)
    return jsonify({'result': result, 'confidence': confidence})


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
