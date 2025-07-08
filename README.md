# Quick Lap (Laptop Recommendation System)

_Documents about the project: [Report][doc]_

Team size: 5 people

- Project Field: Software Engineering, Object-Oriented Programming, Database, LLMs, Chatbot, Natural Language Processing, Data Scraping.

This project addresses that challenge by developing a question-answering and product recommendation system for e-commerce platforms.

To support efficient and accurate product discovery, we experimented with several approaches:

• From traditional keyword-based search and information processing to advanced AI methods such
as Retrieval-Augmented Generation (RAG) using large language models (LLMs).

• By evaluating different LLMs and retrieval pipelines, we aim to identify the most effective combi-
nation for enhancing user experience and reducing search time.

## Demo

https://github.com/user-attachments/assets/189662e1-daa3-4e99-9a8d-9ce0092098d0

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

## Tech Stack

### Frameworks & Libraries

#### Language:
- Java: The primary programming language, evident from file names `.java`, class structures and mentioned libraries/frameworks.

#### User Interface (UI):
- JavaFX: Used for building the graphical user interface. Mentions include:
* FXML: For defining the UI structure declaratively.
* Stage, Scene: Core JavaFX windowing components.
* ChatController, HomeController, etc.: JavaFX controller classes.
* JavaFX UI Controls: Button, Label, TextField, TextArea, ComboBox, ImageView, GridPane, VBox, HBox, StackPane, ScrollPane.
* JavaFX Animation: Timeline, KeyFrame (for the typewriter effect).

### Data Collection & Web Interaction:
• Selenium WebDriver: For automating web browser interaction to scrape laptop data from ”thegioididong.com”.
• ChromeDriver: Specific WebDriver implementation for Google Chrome.
• WebDriverManager: For automatically managing browser driver binaries.

### Database & Data Persistence:
• PostgreSQL: The relational database management system used for storing product data.

• JDBC (Implicit): Likely used for Java-to-PostgreSQL connectivity via the DAO layer, though not explicitly named, it’s the standard.

• CSV (Comma-Separated Values): Used as a file format for storing the scraped laptop data (`saveLaptopsToCsv`).

### AI & Machine Learning Integration:
• External Embedding API: An unspecified external API is used to convert text (product descriptions, user queries) into numerical vector embeddings (via EmbeddingService).

• Mistral AI API: An LLM (Large Language Model) API used for the chatbot functionality to generate responses to user queries (MistralClient interacts with this).

• JSON: Used as the data interchange format for communicating with both the Embedding API and the Mistral AI API.

– Gson library: Explicitly mentioned for parsing JSON responses from the Embedding API.

– `org.json` library: Explicitly mentioned for constructing JSON requests for the Mistral AI API.

### HTTP Communication:
• Java’s built-in HTTP Client: (`java.net.HttpURLConnection` or `java.net.http.HttpClient`)
Used by EmbeddingService and MistralClient to make POST requests to external APIs.

### Configuration:

• `.properties` files: Used for application configuration (e.g., application.properties storing API URLs, keys, database credentials).

### Design Patterns & Architecture:
• Model-View-Controller (MVC) variant: The separation of FXML (View), Controller classes, and Model classes (Product, Laptop) strongly suggests an MVC-like architecture.

• Data Access Object (DAO): ProductDAO, PostgreSqlDAO are used to abstract and encapsulate database interactions.

• Factory Pattern / Abstract Factory Pattern: ProductFactory, SqlFactory, LaptopPostgreSqlFactory are used for creating product objects, decoupling client code from concrete class instantiation.

• Service Layer: Implied by classes like EmbeddingService, ProductSearchService, AlClient, which encapsulate specific business logic or external service interactions.

### Core Java Features:
• Java Collections Framework: List, Map, HashSet, LinkedHashMap are used extensively for data management.

• Generics: Used heavily in DAO and Factory patterns (`<T extends Product>`) for type safety and reusability.

• Object-Oriented Programming (OOP): Encapsulation, Inheritance (Laptop extends Product), Polymorphism are fundamental to the design.

## Algorithms & Techniques

### Information Retrieval & Semantic Search:
• Vector Embeddings: Textual descriptions of products and user queries are converted into dense vector representations. This allows for semantic understanding beyond simple keyword matching.

• Cosine Similarity: The mathematical measure used to determine the similarity between the query vector and product vectors. This is the core of the semantic search functionality (`ProductSearchService.cosineSimilarity`).

<img width="187" alt="cosine" src="https://github.com/user-attachments/assets/f2cc0a06-a154-4a3c-9d68-01d9c6198431" />

• Top-K Ranking/Retrieval: After calculating similarities, the system ranks products and returns the top k most relevant ones to the user’s query.

### AI Chatbot Interaction:
• Prompt Engineering: The MistralClient constructs specific prompts (including user query, relevant product context, and instructions) to guide the LLM in generating useful and relevant responses.

• LLM API Integration: Sending structured requests to the Mistral AI API and parsing its JSON responses to extract the generated text.

### Web Scraping & Data Extraction:
• Automated Browser Navigation: Selenium is used to navigate web pages, scroll to load dynamic content (loadAllProducts).

• HTML Element Selection & Parsing: Identifying and extracting data from specific HTML elements (e.g., li.item, product name, price, specs).

• Data Cleaning & Transformation: Raw scraped data is processed (cleanText, parsePrice, parseInt, parseFloat) to ensure consistency and correct data types.

### User Interface Logic:
• Gradual Text Display (Typewriter Effect): Implemented in ChatController.displayTextGradually using JavaFX Timeline and KeyFrame to enhance the user experience when displaying chatbot messages.

• Client-Side Filtering: The HomeController implements logic to filter the displayed product list based on user selections in ComboBoxes (brand, category, OS) and keyword search.

### Data Management & Persistence:
• Object-Relational Mapping (Conceptual): The DAO layer and methods like mapToDatabase() in model classes facilitate the translation of Java objects to a format suitable for storage in the PostgreSQL relational database.

• CSV Data Serialization: Converting lists of Laptop objects into a CSV file format for storage/export.

_For more information, please read the [Report][doc]_

[doc]: Report.pdf
