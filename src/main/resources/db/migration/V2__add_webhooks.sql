CREATE TABLE webhooks (
    id UUID PRIMARY KEY,
    url TEXT NOT NULL,
    token TEXT,
    tenant_id TEXT
)