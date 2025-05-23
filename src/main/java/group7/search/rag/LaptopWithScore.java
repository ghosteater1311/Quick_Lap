package group7.search.rag;

import group7.model.Laptop;

public class LaptopWithScore {
    public Laptop laptop;
    public double score;

    public LaptopWithScore(Laptop laptop, double score) {
        this.laptop = laptop;
        this.score = score;
    }
}
