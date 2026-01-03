ALTER TABLE feedback_message ADD COLUMN status TEXT;
ALTER TABLE feedback_message ADD COLUMN error_message TEXT;

-- drop unused column
ALTER TABLE feedback_message DROP COLUMN is_analyzed;