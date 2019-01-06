package model;

public class PoctyVozidiel {
    private Integer vsetky;
    private Integer funkcne;

    public PoctyVozidiel(Integer vsetky, Integer funkcne) {
        this.vsetky = vsetky;
        this.funkcne = funkcne;
    }

    public Integer getVsetky() {
        return vsetky;
    }

    public void setVsetky(Integer vsetky) {
        this.vsetky = vsetky;
    }

    public Integer getFunkcne() {
        return funkcne;
    }

    public void setFunkcne(Integer funkcne) {
        this.funkcne = funkcne;
    }
}
