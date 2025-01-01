package com.example.map;

public class Chunk {
    private int cols;
    private int rows;
    private Case[][] grid;

    public Chunk(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        this.grid = new Case[cols][rows];
    }

    public Case getCase(int row, int col) {
        return this.grid[row][col];
    }

    public void setCase(int row, int col, float noiseValue) {
        if(noiseValue<0.4){
            // Eau
            this.grid[row][col] = new Case(new EauType(EauType.VariantWater.CENTRE), null);

        }else if(noiseValue < 0.6){
            // Sable
            this.grid[row][col] = new Case(new SableType(SableType.VariantSable.CENTRE), null);

        }else if(noiseValue < 0.7){
            // Herbe
            this.grid[row][col] = new Case(new HerbeType(HerbeType.VariantHerbe.CLAIR), null);

        }else{
            // Foret
            this.grid[row][col] = new Case(new HerbeType(HerbeType.VariantHerbe.CLAIR),
                    new ArbreElement(ArbreElement.VariantArbre.CLAIR));
        }
    }

    public Case[][] getGrid() {
        return grid;
    }
}
