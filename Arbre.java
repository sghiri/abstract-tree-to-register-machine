import java.util.ArrayList;

public class Arbre {
    private String value;
    private Arbre left;
    private Arbre right;
    private Type type;
    private ArrayList<String> var_list = new ArrayList<>();
    private ArrayList<String> out_put_code = new ArrayList<>();

    public Arbre(String value, Arbre left, Arbre right, Type type) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.type = type;
    }

    public Arbre(String value, Arbre left, Arbre right) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.type = Type.Symbole;
    }

    public String toString(){
        return "(" + aux(this) + ")";
    }

    private String aux(Arbre arbre) {
        if (arbre==null) return "";
        else if(arbre.left == null || arbre.right == null) return arbre.value.toString();
        else return arbre.value.toString() + "(" + aux(arbre.left) + " " + aux(arbre.right) + ")";
    }

    private void get_variable(Arbre arbre){
        if (arbre != null) {
            if (arbre.type == Type.Variable && !this.var_list.contains(arbre.value))
                this.var_list.add(arbre.value);
            this.get_variable(arbre.left);
            this.get_variable(arbre.right);
        }
    }

    private StringBuilder get_operator(String value){
        switch (value){
            case "+":
                return new StringBuilder("\tadd ");
            case "/":
                return new StringBuilder("\tdiv ");
            case "*":
                return new StringBuilder("\tmul ");
            case "-":
                return new StringBuilder("\tsub ");
        }
        return new StringBuilder();
    }

    private StringBuilder parse_tree(Arbre arbre){

        if (arbre != null) {
            switch (arbre.type) {

                case Declaration:
                    return parse_tree(arbre.right).append("\tpop eax\n").append("\tmov ").append(arbre.left.value).append(", eax\n").append("\tpush eax\n");
                case Integer:
                    return new StringBuilder().append("\tmov eax, ").append(arbre.value).append("\n");
                case Symbole:
                    return parse_tree(arbre.left).append(parse_tree(arbre.right)).append("\tpop ebx\n\tpop eax\n").append(get_operator(arbre.value)).append("eax, ebx\n").append("\tpush eax\n");
                case EndLine:
                    return parse_tree(arbre.left).append("\tpop eax\n").append(parse_tree(arbre.right));
            }
        }
        return new StringBuilder();


    }

    public String genrate_code(){

        this.get_variable(this);
        StringBuilder var_list_string = new StringBuilder();
        var_list_string.append("DATA SEGMENT\n");
        for (String s : this.var_list)
            var_list_string.append("\t").append(s).append(" DD\n");
        var_list_string.append("DATA ENDS\nCODE SEGMENT\n").append(this.parse_tree(this));
        var_list_string.append("CODE ENDS");


        return var_list_string.toString();

    }
}