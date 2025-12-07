package com.example.course_service;

import com.example.course_service.entity.Subject;
import com.example.course_service.entity.Topic;
import com.example.course_service.repository.SubjectRepository;
import com.example.course_service.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final SubjectRepository subjectRepository;
    private final TopicRepository topicRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (subjectRepository.count() > 0) {
            log.info("Database already initialized. Skipping data initialization.");
            return;
        }

        log.info("Starting database initialization...");
        initializeSubjectsAndTopics();
        log.info("Database initialization completed successfully!");
    }

    private void initializeSubjectsAndTopics() {
        // Subject 1: Data Structures and Algorithms
        Subject dsa = createSubject("Data Structures and Algorithms",
                "Fundamental data structures and algorithmic techniques for efficient problem solving");
        createTopics(dsa, Arrays.asList(
                new TopicData("Arrays and Strings", "Basic data structures for storing sequences of elements", 1),
                new TopicData("Linked Lists", "Dynamic data structure with nodes and pointers", 1),
                new TopicData("Stacks and Queues", "LIFO and FIFO data structures and their applications", 1),
                new TopicData("Trees and Binary Search Trees", "Hierarchical data structures and tree traversals", 2),
                new TopicData("Hash Tables", "Key-value pair storage with efficient lookup", 2),
                new TopicData("Graphs", "Graph representations and basic graph algorithms", 2),
                new TopicData("Sorting Algorithms", "Quick sort, merge sort, heap sort, and comparison", 2),
                new TopicData("Dynamic Programming", "Optimization technique using memoization and tabulation", 3),
                new TopicData("Greedy Algorithms", "Making locally optimal choices for global optimization", 3),
                new TopicData("Advanced Graph Algorithms", "Dijkstra, Bellman-Ford, Floyd-Warshall, MST", 3)
        ));

        // Subject 2: Database Management Systems
        Subject dbms = createSubject("Database Management Systems",
                "Design, implementation, and management of relational and NoSQL databases");
        createTopics(dbms, Arrays.asList(
                new TopicData("Relational Model", "Tables, relations, keys, and relational algebra", 1),
                new TopicData("SQL Basics", "SELECT, INSERT, UPDATE, DELETE, and basic queries", 1),
                new TopicData("Database Normalization", "1NF, 2NF, 3NF, BCNF and functional dependencies", 2),
                new TopicData("Joins and Subqueries", "INNER, OUTER, CROSS joins and nested queries", 2),
                new TopicData("Indexes and Query Optimization", "Index types, query plans, and performance tuning", 2),
                new TopicData("Transactions and ACID", "Transaction management, isolation levels, and concurrency", 2),
                new TopicData("NoSQL Databases", "MongoDB, Redis, Cassandra, document and key-value stores", 3),
                new TopicData("Database Design", "ER diagrams, schema design, and best practices", 2),
                new TopicData("Stored Procedures and Triggers", "Database automation and business logic", 3),
                new TopicData("Distributed Databases", "Sharding, replication, CAP theorem", 3)
        ));

        // Subject 3: Operating Systems
        Subject os = createSubject("Operating Systems",
                "Core concepts of OS including process management, memory management, and file systems");
        createTopics(os, Arrays.asList(
                new TopicData("Introduction to OS", "OS structure, types, and basic concepts", 1),
                new TopicData("Process Management", "Process states, scheduling, and context switching", 2),
                new TopicData("Threads and Concurrency", "Multithreading, thread synchronization, and deadlocks", 2),
                new TopicData("CPU Scheduling", "FCFS, SJF, Round Robin, Priority scheduling algorithms", 2),
                new TopicData("Memory Management", "Virtual memory, paging, segmentation", 2),
                new TopicData("File Systems", "File organization, directories, and disk management", 2),
                new TopicData("Deadlock Handling", "Detection, prevention, and avoidance strategies", 3),
                new TopicData("Inter-Process Communication", "Pipes, message queues, shared memory, semaphores", 3),
                new TopicData("I/O Systems", "Device drivers, buffering, and I/O scheduling", 3),
                new TopicData("Virtual Machines", "Hypervisors, containerization, and virtualization", 3)
        ));

        // Subject 4: Computer Networks
        Subject networks = createSubject("Computer Networks",
                "Networking fundamentals, protocols, and network architecture");
        createTopics(networks, Arrays.asList(
                new TopicData("Network Basics", "Network types, topologies, and basic terminology", 1),
                new TopicData("OSI and TCP/IP Models", "Layer architecture and protocol stacks", 1),
                new TopicData("IP Addressing", "IPv4, IPv6, subnetting, and CIDR", 2),
                new TopicData("TCP and UDP", "Transport layer protocols and their differences", 2),
                new TopicData("HTTP and HTTPS", "Web protocols, REST, and secure communication", 2),
                new TopicData("DNS and DHCP", "Domain name resolution and dynamic IP assignment", 2),
                new TopicData("Routing Algorithms", "Distance vector, link state, and BGP", 3),
                new TopicData("Network Security", "Firewalls, VPNs, and intrusion detection", 3),
                new TopicData("Wireless Networks", "WiFi, cellular networks, and mobile communication", 2),
                new TopicData("Software Defined Networks", "SDN architecture, OpenFlow, and network programmability", 3)
        ));

        // Subject 5: Software Engineering
        Subject se = createSubject("Software Engineering",
                "Software development methodologies, design patterns, and best practices");
        createTopics(se, Arrays.asList(
                new TopicData("SDLC Models", "Waterfall, Agile, Scrum, and development methodologies", 1),
                new TopicData("Requirements Engineering", "Gathering, analysis, and specification of requirements", 1),
                new TopicData("Software Design Principles", "SOLID, DRY, KISS, and clean code practices", 2),
                new TopicData("Design Patterns", "Creational, structural, and behavioral patterns", 2),
                new TopicData("Version Control", "Git, branching strategies, and collaboration", 1),
                new TopicData("Testing Strategies", "Unit, integration, system, and acceptance testing", 2),
                new TopicData("CI/CD Pipelines", "Continuous integration and deployment automation", 3),
                new TopicData("Code Review", "Best practices for reviewing and maintaining code quality", 2),
                new TopicData("Microservices Architecture", "Service-oriented design and distributed systems", 3),
                new TopicData("DevOps Practices", "Infrastructure as code, monitoring, and site reliability", 3)
        ));

        // Subject 6: Web Development
        Subject web = createSubject("Web Development",
                "Modern web technologies including frontend, backend, and full-stack development");
        createTopics(web, Arrays.asList(
                new TopicData("HTML and CSS Basics", "Markup and styling fundamentals", 1),
                new TopicData("JavaScript Fundamentals", "Variables, functions, DOM manipulation", 1),
                new TopicData("Responsive Design", "Mobile-first design, flexbox, and grid layouts", 2),
                new TopicData("React.js", "Component-based UI development with hooks", 2),
                new TopicData("Node.js and Express", "Backend development with JavaScript runtime", 2),
                new TopicData("RESTful APIs", "API design, HTTP methods, and status codes", 2),
                new TopicData("Authentication and Authorization", "JWT, OAuth, session management", 3),
                new TopicData("State Management", "Redux, Context API, and application state", 3),
                new TopicData("WebSockets", "Real-time bidirectional communication", 3),
                new TopicData("Web Performance Optimization", "Caching, lazy loading, and bundle optimization", 3)
        ));

        // Subject 7: Artificial Intelligence
        Subject ai = createSubject("Artificial Intelligence",
                "Machine learning, deep learning, and AI algorithms and applications");
        createTopics(ai, Arrays.asList(
                new TopicData("Introduction to AI", "History, applications, and AI fundamentals", 1),
                new TopicData("Search Algorithms", "BFS, DFS, A*, heuristic search", 1),
                new TopicData("Machine Learning Basics", "Supervised, unsupervised, and reinforcement learning", 2),
                new TopicData("Linear Regression", "Prediction using linear models", 2),
                new TopicData("Classification Algorithms", "Logistic regression, decision trees, SVM", 2),
                new TopicData("Neural Networks", "Perceptrons, backpropagation, activation functions", 3),
                new TopicData("Deep Learning", "CNNs, RNNs, LSTMs, and transformers", 3),
                new TopicData("Natural Language Processing", "Text processing, sentiment analysis, language models", 3),
                new TopicData("Computer Vision", "Image processing, object detection, segmentation", 3),
                new TopicData("Reinforcement Learning", "Q-learning, policy gradients, and game AI", 3)
        ));

        // Subject 8: Cybersecurity
        Subject security = createSubject("Cybersecurity",
                "Security principles, cryptography, network security, and ethical hacking");
        createTopics(security, Arrays.asList(
                new TopicData("Security Fundamentals", "CIA triad, threats, vulnerabilities, and risks", 1),
                new TopicData("Cryptography Basics", "Symmetric and asymmetric encryption", 2),
                new TopicData("Public Key Infrastructure", "Digital certificates, SSL/TLS, and PKI", 2),
                new TopicData("Network Security", "Firewalls, IDS/IPS, and network hardening", 2),
                new TopicData("Web Application Security", "OWASP Top 10, XSS, CSRF, SQL injection", 3),
                new TopicData("Penetration Testing", "Ethical hacking methodologies and tools", 3),
                new TopicData("Malware Analysis", "Types of malware and reverse engineering", 3),
                new TopicData("Authentication Systems", "Multi-factor authentication and biometrics", 2),
                new TopicData("Incident Response", "Security incident handling and forensics", 3),
                new TopicData("Blockchain Security", "Cryptocurrency security and smart contract auditing", 3)
        ));

        // Subject 9: Cloud Computing
        Subject cloud = createSubject("Cloud Computing",
                "Cloud platforms, distributed systems, and cloud-native architectures");
        createTopics(cloud, Arrays.asList(
                new TopicData("Cloud Computing Basics", "IaaS, PaaS, SaaS, and cloud models", 1),
                new TopicData("AWS Fundamentals", "EC2, S3, RDS, and core AWS services", 2),
                new TopicData("Docker and Containers", "Containerization and Docker basics", 2),
                new TopicData("Kubernetes", "Container orchestration and K8s architecture", 3),
                new TopicData("Serverless Computing", "AWS Lambda, Azure Functions, event-driven architecture", 3),
                new TopicData("Cloud Storage Solutions", "Object storage, block storage, and databases", 2),
                new TopicData("Cloud Networking", "VPC, subnets, load balancers, and CDN", 2),
                new TopicData("Cloud Security", "IAM, encryption, compliance, and best practices", 3),
                new TopicData("Microservices in Cloud", "Service mesh, API gateways, and distributed tracing", 3),
                new TopicData("Cloud Cost Optimization", "Resource management and cost-effective strategies", 2)
        ));

        // Subject 10: Mobile App Development
        Subject mobile = createSubject("Mobile App Development",
                "Native and cross-platform mobile application development");
        createTopics(mobile, Arrays.asList(
                new TopicData("Mobile Development Basics", "Native vs cross-platform development", 1),
                new TopicData("Android Development", "Java/Kotlin, Activities, Fragments, and Android Studio", 2),
                new TopicData("iOS Development", "Swift, UIKit, SwiftUI, and Xcode", 2),
                new TopicData("React Native", "Cross-platform development with JavaScript", 2),
                new TopicData("Flutter", "Dart language and widget-based UI development", 2),
                new TopicData("Mobile UI/UX Design", "Material Design, Human Interface Guidelines", 1),
                new TopicData("Mobile Data Storage", "SQLite, Room, Core Data, and local persistence", 2),
                new TopicData("Push Notifications", "FCM, APNs, and notification handling", 3),
                new TopicData("Mobile App Performance", "Optimization, profiling, and memory management", 3),
                new TopicData("App Deployment", "Play Store and App Store submission process", 2)
        ));
    }

    private Subject createSubject(String name, String description) {
        Subject subject = Subject.builder()
                .name(name)
                .description(description)
                .build();
        return subjectRepository.save(subject);
    }

    private void createTopics(Subject subject, List<TopicData> topicsData) {
        for (TopicData data : topicsData) {
            Topic topic = Topic.builder()
                    .name(data.name)
                    .description(data.description)
                    .difficultyLevel(data.difficultyLevel)
                    .subject(subject)
                    .build();
            topicRepository.save(topic);
        }
    }

    private static class TopicData {
        String name;
        String description;
        Integer difficultyLevel;

        TopicData(String name, String description, Integer difficultyLevel) {
            this.name = name;
            this.description = description;
            this.difficultyLevel = difficultyLevel;
        }
    }
}