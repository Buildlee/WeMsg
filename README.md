# WeMsg 📨

![License](https://img.shields.io/badge/license-MIT-blue)
![Python](https://img.shields.io/badge/python-3.13-3776AB)
![React](https://img.shields.io/badge/react-19-61DAFB)
![FastAPI](https://img.shields.io/badge/fastapi-0.109-009688)

**WeMsg** 是一个现代化的微信数据导出与分析工具。基于 Python FastAPI + React 构建，旨在提供安全、流畅的本地数据管理体验。

## ✨ 功能特性
- **多格式导出**: 支持 HTML, PDF, Excel 等格式
- **数据可视化**: 生成聊天**词云**与年度报告
- **隐私安全**: 纯本地离线运行

## 🚀 快速开始

### 后端 (Backend)
```bash
pip install -r backend/requirements.txt
python -m uvicorn backend.main:app --reload
```

### 前端 (Frontend)
```bash
cd frontend
npm install
npm run dev
```

访问: `http://localhost:5173`

## �️ 技术栈
- **Core**: Python 3.13, FastAPI, SQLCipher
- **UI**: React 19, TypeScript, Tailwind CSS

---
*Developed by Buildlee*
