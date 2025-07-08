# Quick Lap (Laptop Recommendation System)

Team size: 5 people

- Project Field: Software Engineering, Object-Oriented Programming, Database, LLMs, Chatbot, Natural Language Processing, Data Scraping.

This project addresses that challenge by developing a question-answering and product recommendation system for e-commerce platforms.

To support efficient and accurate product discovery, we experimented with several approaches:

• From traditional keyword-based search and information processing to advanced AI methods such
as Retrieval-Augmented Generation (RAG) using large language models (LLMs).

• By evaluating different LLMs and retrieval pipelines, we aim to identify the most effective combi-
nation for enhancing user experience and reducing search time.

## System Design

<img width="543" alt="Use Case" src="https://github.com/user-attachments/assets/d30a1dd8-23de-48bc-8dfe-6dbc85713de8" />

## Package Diagram

<img width="633" alt="Package Diagram" src="https://github.com/user-attachments/assets/b82d3d9f-f1e4-4401-b857-8c4701c48d45" />

### `group7.model`

The `group7.model` package is responsible for defining the core data structures, or ”models,” that represent the entities within the application. It establishes a clear hierarchy for product-related data, facilitating organized data management, reusability, and extensibility.

### `group7.ui`

Create an interactive interface for the user, including components like buttons, search bars, images, etc.
This links to corresponding services to provide functionality to the user.

• Main components of UI code: Code originating from the `Main.java` file; FXML files for building the interface; Controller classes linked to their respective FXML files.

### `group7.data`

#### `group7.data.collector (DataCollector.java), group7.data.storage` 

The `DataCollector` class is designed to collect information about laptop products from an online retail website (https://www.thegioididong.com) by using Selenium WebDriver to automate web browsing, extract product information, and store it in a CSV file. The data is organized into Laptop objects to ensure structure and ease of use in other applications.

### `group7.retrieval`

Package `group7.retrieval` is designed to handle tasks related to information retrieval in the system, specifically searching for products based on vector embeddings and calculating similarity. The system’s recommendation model works by representing products and user queries as numerical vectors and calculating cosine similarity, then providing suggestions to the user.

### `group7.llm`

Package `group7.llm` is designed to integrate and use Large Language Models (LLMs) to provide responses based on artificial intelligence, especially in the context of product consultation.

### `group7.config`

Package `group7.config` is responsible for managing application settings, such as API credentials and service URLs. Its main goal is to abstract configuration details away from the core application logic, allowing for easy updates and maintenance without modifying the source code.

## How to Run

1. Create a virtual environment with Python 3.11:
   ```bash
   python3.11 -m venv .venv
   ```
