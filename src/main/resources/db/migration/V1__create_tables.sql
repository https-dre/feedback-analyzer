CREATE TABLE analysis_batch (
    id UUID PRIMARY KEY,
    tenant_id VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP
);

CREATE TABLE feedback_message (
    id UUID PRIMARY KEY,
    batch_id UUID NOT NULL,
    original_body TEXT NOT NULL,

    -- AI generated fields
    category VARCHAR(100),
    sentiment VARCHAR(50),
    summary TEXT,
    is_analyzed BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_batch
        FOREIGN KEY(batch_id) REFERENCES analysis_batch(id)
)