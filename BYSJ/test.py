import tkinter as tk
from tkinter import filedialog
from PIL import Image, ImageTk
import torch
from torchvision import transforms
from torchvision.models import resnet50, vgg16
import torch.nn as nn



def use_resnet():
    global model
    # 加载模型
    model = resnet50(weights=False)
    model.fc = nn.Linear(model.fc.in_features, 2)
    model.load_state_dict(torch.load('model_Resnet50.pth'))
    model.eval()


# 使用vgg
def use_vgg():
    global model
    model = vgg16(weights=False)
    model.classifier[6] = nn.Linear(model.classifier[6].in_features, 2)
    model.load_state_dict(torch.load('model_vgg16.pth'))
    model.eval()


use_resnet()

# 定义转换
transform = transforms.Compose([
    transforms.Resize(256),
    transforms.CenterCrop(224),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
])

# 创建主窗口
root = tk.Tk()
root.title("COVID-19辅助诊断")

# 设置主窗口大小
root.geometry("800x600")

# 添加一个用于显示图像的标签
label_image = tk.Label(root)
label_image.pack()

# 添加一个用于显示结果的标签
label_prediction = tk.Label(root, text="诊断结果为:空", font=('Arial', 14))
label_prediction.pack()


# 加载图像并进行预测的函数
def load_image():
    path = filedialog.askopenfilename()
    if path:
        image = Image.open(path)
        image_display = ImageTk.PhotoImage(image.resize((256, 256)))
        label_image.config(image=image_display)
        label_image.image = image_display  # 保持引用

        # 图像预处理并预测
        image_tensor = transform(image).unsqueeze(0)
        with torch.no_grad():
            outputs = model(image_tensor)
            _, predicted = torch.max(outputs, 1)
        result = '阳性' if predicted.item() == 1 else '阴性'
        label_prediction.config(text=f"诊断结果为: {result} 模型准确率为:78%")


# 添加一个按钮，用于打开文件对话框
btn_load = tk.Button(root, text="上传影像", command=load_image, width=20, height=5)
btn_load.pack()

# 添加一个按钮，使用VGG16模型
btn_vgg = tk.Button(root, text="vgg_model", command=use_vgg, width=20, height=5)
btn_vgg.pack()

# 添加一个按钮，使用Resnet50模型
btn_resnet = tk.Button(root, text="resnet_model", command=use_resnet, width=20, height=5)
btn_resnet.pack()

# 运行主循环
root.mainloop()
