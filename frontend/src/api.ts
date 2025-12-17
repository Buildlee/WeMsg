
export interface Contact {
    username: string;
    alias?: string;
    nickname?: string;
    remark?: string;
    signature?: string;
}

export interface Message {
    msgId: number;
    msgSvrId: number;
    type: string;
    subType: number;
    isSender: number;
    createTime: number;
    talker: string;
    content: string;
    imgPath?: string;
}

const API_BASE = '/api';

export const api = {
    async connect(dbPath: string, password?: string): Promise<boolean> {
        const params = new URLSearchParams();
        if (dbPath) params.append('dbPath', dbPath);
        if (password) params.append('password', password);

        try {
            const res = await fetch(`${API_BASE}/connect?${params.toString()}`, {
                method: 'POST'
            });
            const text = await res.text();
            return text === '已连接'; // Checking against the specific string returned by backend
        } catch (e) {
            console.error('Connection failed:', e);
            return false;
        }
    },

    async getContacts(dbPath: string, password?: string): Promise<Contact[]> {
        const params = new URLSearchParams();
        if (dbPath) params.append('dbPath', dbPath);
        if (password) params.append('password', password);

        try {
            const res = await fetch(`${API_BASE}/contacts?${params.toString()}`);
            if (!res.ok) throw new Error('Failed to fetch contacts');
            return await res.json();
        } catch (e) {
            console.error('Fetch contacts failed:', e);
            return [];
        }
    },

    async getMessages(talkerId: string, dbPath: string, password?: string): Promise<Message[]> {
        const params = new URLSearchParams();
        params.append('talkerId', talkerId);
        if (dbPath) params.append('dbPath', dbPath);
        if (password) params.append('password', password);

        try {
            const res = await fetch(`${API_BASE}/messages?${params.toString()}`);
            if (!res.ok) throw new Error('Failed to fetch messages');
            return await res.json();
        } catch (e) {
            console.error('Fetch messages failed:', e);
            return [];
        }
    }
};
