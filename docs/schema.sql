-- ============================================================================
-- UG Campus Smart Service Operations Optimizer
-- PostgreSQL Database Schema
-- University of Ghana, Legon — DCIT 204/308 DSA Project
-- ============================================================================
-- Run this file once to create all required tables:
--   psql -U postgres -d campusops -f schema.sql
--
-- Or in pgAdmin: Open Query Tool → paste this → Execute (F5)
-- ============================================================================

-- 1. Locations — every place on campus (60 records)
CREATE TABLE IF NOT EXISTS locations (
    location_id     SERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    area            VARCHAR(80),
    type            VARCHAR(50),
    latitude        DECIMAL(8, 4),
    longitude       DECIMAL(8, 4)
);

-- 2. Routes — paths connecting locations (110 records)
CREATE TABLE IF NOT EXISTS routes (
    route_id            SERIAL PRIMARY KEY,
    from_location_id    INT NOT NULL REFERENCES locations(location_id),
    to_location_id      INT NOT NULL REFERENCES locations(location_id),
    distance_m          INT NOT NULL,
    avg_time_min        INT NOT NULL,
    traffic_factor      DECIMAL(3, 1) DEFAULT 1.0
);

-- 3. Resources — maintenance staff, shuttles, vehicles (35 records)
CREATE TABLE IF NOT EXISTS resources (
    resource_id         SERIAL PRIMARY KEY,
    type                VARCHAR(50) NOT NULL,
    home_location_id    INT REFERENCES locations(location_id),
    capacity            INT DEFAULT 1,
    availability_status VARCHAR(20) DEFAULT 'available'
);

-- 4. Service Requests — maintenance complaints (310 records)
CREATE TABLE IF NOT EXISTS service_requests (
    request_id              SERIAL PRIMARY KEY,
    source_location_id      INT NOT NULL REFERENCES locations(location_id),
    destination_location_id INT REFERENCES locations(location_id),
    category                VARCHAR(30) NOT NULL,
    urgency_level           INT NOT NULL CHECK (urgency_level BETWEEN 1 AND 5),
    time_submitted          TIMESTAMP NOT NULL DEFAULT NOW(),
    deadline                TIMESTAMP,
    status                  VARCHAR(20) DEFAULT 'pending',
    assigned_resource_id    INT REFERENCES resources(resource_id)
);

-- 5. Algorithm Runs — performance experiment results (30+ records)
CREATE TABLE IF NOT EXISTS algorithm_runs (
    run_id          SERIAL PRIMARY KEY,
    algorithm_name  VARCHAR(50) NOT NULL,
    input_size      INT NOT NULL,
    time_ns         BIGINT NOT NULL,
    memory_kb       BIGINT,
    date_run        TIMESTAMP DEFAULT NOW()
);

-- 6. Audit Events — stack-based undo/audit log
CREATE TABLE IF NOT EXISTS audit_events (
    event_id        SERIAL PRIMARY KEY,
    event_type      VARCHAR(30) NOT NULL,
    entity_type     VARCHAR(30),
    entity_id       INT,
    description     TEXT,
    timestamp       TIMESTAMP DEFAULT NOW()
);

-- ============================================================================
-- Indexes for faster queries (optional but recommended)
-- ============================================================================
CREATE INDEX IF NOT EXISTS idx_requests_status ON service_requests(status);
CREATE INDEX IF NOT EXISTS idx_requests_urgency ON service_requests(urgency_level);
CREATE INDEX IF NOT EXISTS idx_requests_location ON service_requests(source_location_id);
CREATE INDEX IF NOT EXISTS idx_routes_from ON routes(from_location_id);
CREATE INDEX IF NOT EXISTS idx_routes_to ON routes(to_location_id);
CREATE INDEX IF NOT EXISTS idx_resources_status ON resources(availability_status);
CREATE INDEX IF NOT EXISTS idx_algorithm_runs_name ON algorithm_runs(algorithm_name);
