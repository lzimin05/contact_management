INSERT INTO companies (name, industry, website, address) VALUES
('Apple Inc.', 'Technology', 'https://www.apple.com', 'Cupertino, California'),
('Microsoft Corporation', 'Technology', 'https://www.microsoft.com', 'Redmond, Washington'),
('Google LLC', 'Technology', 'https://www.google.com', 'Mountain View, California'),
('Samsung Electronics', 'Electronics', 'https://www.samsung.com', 'Seoul, South Korea'),
('Tesla Inc.', 'Automotive', 'https://www.tesla.com', 'Austin, Texas');

INSERT INTO contacts (name, email, phone, position, company_id, notes) VALUES
('John Smith', 'john.smith@apple.com', '+12125552368', 'Senior Manager', 1, 'Main contact at Apple'),
('Alice Johnson', 'alice.johnson@microsoft.com', '+14255434567', 'Product Lead', 2, 'Responsible for cloud products'),
('Bob Williams', 'bob.williams@google.com', '+16505551234', 'Engineer', 3, 'Working on AI projects'),
('Diana Brown', 'diana.brown@samsung.com', '+82226888888', 'Sales Manager', 4, 'Regional sales director'),
('Eve Davis', 'eve.davis@tesla.com', '+15129357700', 'HR Specialist', 5, 'Recruitment team'),
('Frank Miller', 'frank.miller@apple.com', '+12125557890', 'Consultant', 1, 'Advisory role');

INSERT INTO interactions (contact_id, notes, date, created_at) VALUES
(1, 'Discussed Q4 partnership goals', '2024-12-05 14:30:00', CURRENT_TIMESTAMP),
(1, 'Follow-up meeting scheduled', '2024-12-03 10:15:00', CURRENT_TIMESTAMP),
(2, 'Review cloud migration strategy', '2024-12-04 15:45:00', CURRENT_TIMESTAMP),
(3, 'AI project presentation', '2024-12-02 09:00:00', CURRENT_TIMESTAMP),
(4, 'Sales negotiation for new product line', '2024-12-01 16:20:00', CURRENT_TIMESTAMP),
(5, 'Interview round 2 scheduled', '2024-11-30 11:00:00', CURRENT_TIMESTAMP),
(6, 'Initial consultation', '2024-11-28 13:30:00', CURRENT_TIMESTAMP);
