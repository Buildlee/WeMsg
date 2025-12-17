from fastapi import FastAPI, Response, Query
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import List, Optional
import time

app = FastAPI()

# Configure CORS to allow requests from the frontend dev server
origins = [
    "http://localhost:5173",
    "http://127.0.0.1:5173",
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Models
class Contact(BaseModel):
    username: str
    alias: Optional[str] = None
    nickname: Optional[str] = None
    remark: Optional[str] = None
    signature: Optional[str] = None

class Message(BaseModel):
    msgId: int
    msgSvrId: int
    type: str
    subType: int
    isSender: int
    createTime: int
    talker: str
    content: str
    imgPath: Optional[str] = None

@app.get("/")
def read_root():
    return {"message": "WeMsg Backend is running"}

@app.get("/api/health")
def health_check():
    return {"status": "ok"}

@app.post("/api/connect")
def connect_db(dbPath: Optional[str] = Query(None), password: Optional[str] = Query(None)):
    # Mock connection success
    # In real implementation, this would verify the SQLite DB and password
    if not dbPath:
        return Response(content="数据库路径不能为空", status_code=400)
    return Response(content="已连接", media_type="text/plain")

@app.get("/api/contacts")
def get_contacts(dbPath: Optional[str] = None, password: Optional[str] = None):
    # Mock contacts data
    mock_contacts = [
        {"username": "user1", "nickname": "Alice", "remark": "Friend A", "signature": "Hello world"},
        {"username": "user2", "nickname": "Bob", "alias": "bobby", "signature": "Busy"},
        {"username": "user3", "nickname": "Charlie", "remark": "Work", "signature": "Available"},
    ]
    return mock_contacts

@app.get("/api/messages")
def get_messages(talkerId: str, dbPath: Optional[str] = None, password: Optional[str] = None):
    # Mock messages data
    current_time = int(time.time() * 1000)
    mock_messages = [
        {
            "msgId": 1,
            "msgSvrId": 101,
            "type": "text",
            "subType": 0,
            "isSender": 0,
            "createTime": current_time - 100000,
            "talker": talkerId,
            "content": f"Hello from {talkerId}!"
        },
        {
            "msgId": 2,
            "msgSvrId": 102,
            "type": "text",
            "subType": 0,
            "isSender": 1,
            "createTime": current_time - 50000,
            "talker": talkerId,
            "content": "Hi there! This is a mock reply."
        },
         {
            "msgId": 3,
            "msgSvrId": 103,
            "type": "text",
            "subType": 0,
            "isSender": 0,
            "createTime": current_time - 10000,
            "talker": talkerId,
            "content": "How is the testing going?"
        }
    ]
    return mock_messages
