
import { useState } from 'react'
import { api } from './api'
import type { Contact } from './api'

function App() {
  const [dbPath, setDbPath] = useState('')
  const [password, setPassword] = useState('')
  const [isConnected, setIsConnected] = useState(false)
  const [contacts, setContacts] = useState<Contact[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const handleConnect = async () => {
    setLoading(true)
    setError('')
    try {
      const connected = await api.connect(dbPath, password)
      if (connected) {
        setIsConnected(true)
        // Fetch contacts immediately after connection
        const fetchedContacts = await api.getContacts(dbPath, password)
        setContacts(fetchedContacts)
      } else {
        setError('Connection failed. Please check path and password.')
      }
    } catch (err) {
      setError('An error occurred during connection.')
      console.error(err)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="flex h-screen bg-slate-50 text-slate-900 font-sans">
      {/* Sidebar */}
      <div className="w-80 bg-white border-r border-slate-200 flex flex-col">
        <div className="p-4 border-b border-slate-100 bg-slate-50">
          <h1 className="text-2xl font-bold text-slate-800 tracking-tight">WeMsg</h1>
          <div className="text-xs text-slate-500 mt-1">WeChat Data Recovery</div>
        </div>

        {/* Connection Status / Form */}
        {!isConnected ? (
          <div className="p-4 space-y-3">
            <div className="text-sm font-medium text-slate-700">Database Connection</div>
            <input
              type="text"
              placeholder="DB Path (e.g. C:\Users\..\MSG.db)"
              className="w-full text-sm p-2 border border-slate-300 rounded focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
              value={dbPath}
              onChange={(e) => setDbPath(e.target.value)}
            />
            <input
              type="password"
              placeholder="Password (Optional)"
              className="w-full text-sm p-2 border border-slate-300 rounded focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <button
              onClick={handleConnect}
              disabled={loading}
              className="w-full bg-blue-600 hover:bg-blue-700 text-white text-sm font-medium py-2 rounded transition-colors disabled:opacity-50"
            >
              {loading ? 'Connecting...' : 'Connect'}
            </button>
            {error && <div className="text-xs text-red-500 mt-1">{error}</div>}
          </div>
        ) : (
          <div className="flex-1 overflow-y-auto">
            <div className="p-2">
              <div className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-2 px-2">Contacts ({contacts.length})</div>
              <div className="space-y-1">
                {contacts.map((contact, idx) => (
                  <div key={contact.username || idx} className="p-3 hover:bg-slate-50 rounded-lg cursor-pointer transition-colors group">
                    <div className="flex items-center space-x-3">
                      <div className="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-600 font-bold text-sm">
                        {(contact.nickname?.[0] || contact.username?.[0] || '?').toUpperCase()}
                      </div>
                      <div className="flex-1 min-w-0">
                        <div className="text-sm font-medium text-slate-900 truncate group-hover:text-blue-600">
                          {contact.remark || contact.nickname || contact.username}
                        </div>
                        <div className="text-xs text-slate-500 truncate">
                          {contact.username}
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
                {contacts.length === 0 && (
                  <div className="text-sm text-slate-400 text-center py-4">No contacts found</div>
                )}
              </div>
            </div>
          </div>
        )}
      </div>

      {/* Main Content Area */}
      <div className="flex-1 flex flex-col bg-slate-50">
        {!isConnected ? (
          <div className="flex-1 flex flex-col items-center justify-center text-slate-400">
            <div className="text-6xl mb-4">💬</div>
            <p className="text-lg font-medium">Please connect to a database to start</p>
          </div>
        ) : (
          <div className="flex-1 flex items-center justify-center">
            <div className="text-center text-slate-400">
              <div className="text-4xl mb-2">👋</div>
              <p>Select a contact to view messages</p>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}

export default App

