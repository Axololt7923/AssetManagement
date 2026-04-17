-- Clear existing data (optional, but good for testing)
TRUNCATE TABLE assets CASCADE;
TRUNCATE TABLE employees CASCADE;
TRUNCATE TABLE departments CASCADE;

-- 1 Department
INSERT INTO departments (id, name)
VALUES ('7f3f3898-3f82-45e0-a7d1-9f20c4e10001', 'IT Department');

-- 3 Employees (linking to the same department)
INSERT INTO employees (id, name, email, password, department_id, employee_role, active)
VALUES
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'John Doe', 'john.doe@company.com', 'password123', '7f3f3898-3f82-45e0-a7d1-9f20c4e10001', 'EMPLOYEE', true),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'Jane Smith', 'jane.smith@company.com', 'password123', '7f3f3898-3f82-45e0-a7d1-9f20c4e10001', 'EMPLOYEE', true),
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'Alice Johnson', 'alice.johnson@company.com', 'password123', '7f3f3898-3f82-45e0-a7d1-9f20c4e10001', 'ASSETS_MANAGER', true);

-- 6 Assets (linking to employees)
INSERT INTO assets (id, name, description, asset_type, employee_id, price)
VALUES
    ('b1111111-1111-1111-1111-111111111111', 'Dell Latitude 7420', 'Standard issue laptop for developers', 'electronic', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 1200.50),
    ('b1111111-1111-1111-1111-111111111112', 'Ergonomic Desk Chair', 'High-back mesh chair', 'furniture', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 350.00),
    ('b1111111-1111-1111-1111-111111111113', 'MacBook Pro 14"', 'M2 Pro laptop for design team', 'electronic', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 2499.00),
    ('b1111111-1111-1111-1111-111111111114', 'Dual Monitor Stand', 'Adjustable arm for two monitors', 'electronic', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 85.00),
    ('b1111111-1111-1111-1111-111111111115', 'HP EliteBook 840', 'Backup laptop for management', 'electronic', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 1100.00),
    ('b1111111-1111-1111-1111-111111111116', 'Standing Desk', 'Motorized height-adjustable desk', 'furniture', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 650.00);
