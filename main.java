public class main {
    public static void main(String arg[]) {

        // Let prixHt 200
        Arbre feuille = new Arbre("200", null, null, Type.Integer);
        Arbre feuille2 = new Arbre("prixHT", null, null, Type.Variable);
        Arbre let1 = new Arbre("LET", feuille2, feuille, Type.Declaration);

        // prixHT * 119
        Arbre feuill3 = new Arbre("119", null, null, Type.Integer);
        Arbre feuille4 = new Arbre("prixHT", null, null, Type.Variable);
        Arbre multi = new Arbre("*", feuille4, feuill3);

        // prixHT * 119 / 100
        Arbre division = new Arbre("/", multi, new Arbre("100", null, null, Type.Integer));

        // let prixTtc prixHT * 119 / 100
        Arbre let2 = new Arbre("LET", new Arbre("prixTtc", null, null, Type.Variable), division,
                Type.Declaration);

        // TOUT
        Arbre arbre = new Arbre(";", let1, let2, Type.EndLine);

        System.out.println(arbre.genrate_code());
        System.out.println(arbre);
    }
}