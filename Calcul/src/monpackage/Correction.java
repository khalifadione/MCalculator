package monpackage;

import java.util.LinkedList;

public class Correction

{
   protected String []         number= {"0","1","2","3","4","5","6","7","8","9","."}; 
   protected int[]           precedence = {1,1,2,2,3}; // pour algorithme de Diskra
   protected String            variable="x";
   protected String            separator=","; 
   protected String            leftBracket="("; 
   protected String            rightBracket=")";
   protected String            operators="+-*/^";
   protected String            associativity = "llllr"; // pour algorithme de Diskra
   protected String[]          constants={"e", "pi", "phi"};
   protected String[]         functions = {"ln","sin","cos","tan","arcsin",
                                           "arccos","arctan","exp","abs",
                                              "sqrt","sinh","cosh", "tanh" ,
                                             "arccosh","arcsinh","arctanh"};

   protected   String expression;
   protected     String intervalle= "" ;
   protected  double borne_inferieur = 0;
   protected  double borne_superieur =0 ;
    public Correction(String expression)
     {
     this.expression = expression ; 
     }


// la formule finale de expression mathematique 
   public String final_expression_formula()
    {

        if(this.expression.length()==0)
              return "ERROR";
        this.expression= Extract_Interval(this.expression);

        get_bornes  ( interval_Analyser(intervalle));

        this.expression=with_out_spacing(this.expression);
         if(this.expression.length()==0)
              return "ERROR";
    	this.expression=unary_to_binary( this.expression);
     return correction_3_(correction_2_(correction_1_( this.expression)));



    }

  // cette methode trasforme tout operateur unaire vers un operateur binaire 
  //  exemple cos(-x) --> devient cos(0-x)  Bien sur condition qui soit précédé par une parenthése
  public  String unary_to_binary(String expression)
    {
        String resultat;
    // si expression mathematique debute acev un operateur (-ou+ )ajouter 0 au debut     
        if(Character.toString(expression.charAt(0)).equals("-"))
        {resultat="0"+Character.toString(expression.charAt(0));

        }
      else
        resultat=Character.toString(expression.charAt(0));

        if(expression.length()==2)
            return resultat+Character.toString(expression.charAt(1));
        char current, next=' '; 
 /* sinon vérufier si aprés chaque parenthése si i'ya un operateur unaire trasformer en binaire 
  exmple cos(-sin(-3x)) ---> cos(0-sin(0-3x))
  */       
        for (int i=1 ;i<expression.length()-1;i++)
         {
             int j=i+1 ;
             current = expression.charAt(i);
        next = expression.charAt(j);
if(Character.toString(current).equals("(") && Character.toString(next).equals("-")  || Character.toString(current).equals("(") && Character.toString(next).equals("+"))
 
       {
        resultat+=Character.toString(current)+"0";
             
         }
         // sinon concaténer le reste 
         else
         resultat+=Character.toString(current);
         
    }
    resultat+=Character.toString(next);

    return resultat;
 }





// vérifier que un caractére est un nombre (ou double)
  public  boolean is_numbre( String caracter)
    {
       boolean check = false;  
       for (String element  : number)
        {
        
          if (caracter.contains(element))
             check=true  ; 

        }
      return check; 

   }    


public String correction_3_ (String expression)
  {
   int j=0; 
      String ma_chaine= new String ();
    char current,next=' ';
    
       for(int i=0; i<expression.length()-1;i++ )
        {
            j=i+1;
        current = expression.charAt(i);
        next = expression.charAt(j);
    if(Character.toString(current).equals(variable)&& !( Character.toString(next).equals(")") || Character.toString(next).equals("+") || Character.toString(next).equals("-") || Character.toString(next).equals("/") || Character.toString(next).equals("*") ||Character.toString(next).equals(" ") ||Character.toString(next).equals("^")  || Character.toString(next).equals("p")   ) )
          ma_chaine+=Character.toString(current)+"*";

          else 
           ma_chaine+=Character.toString(current);

         }
       ma_chaine+=Character.toString(next);
          return ma_chaine ; 
   }










// inserer la la multiplication à la place de concaténation (3 methode de correctiion)
 // cos(x)3 --> cos(x)*3




 public String correction_1_ (String expression)
  {
       String keys = new String (operators+rightBracket+" ");
       String ma_chaine="";
       int j=0; 
       char current, next=' '; 
       for (int i=0 ; i<expression.length()-1;i++)
      {   
         j=i+1;
        current = expression.charAt(i);
        next = expression.charAt(j);
 // si le charactére courant est un nombre et le suivent n'est pas un nombre ou un autre operateur ou right_bracket       
         if (is_numbre (Character.toString(current)  )&& ! keys.contains(Character.toString(next)) && !is_numbre (Character.toString(next))   )
          {
            ma_chaine+=Character.toString(current)+"*";
        }
   
         else
             ma_chaine+=Character.toString(current);
    }

        ma_chaine+=Character.toString(next);
        // return the final result as string 
        return ma_chaine ; 
  }




   public static String correction_2_ (String expression)
  {
      String ma_chaine = new String();
    
    char current,next=' ';
       for(int i=0; i<expression.length()-1;i++ )
        {
          current = expression.charAt(i);
          next= expression.charAt(i+1);
 // vérifier si opérateur courant est une parenthése et le suivent n'est pas une parenthése fermante  ou un operateur (+-/^" ")          
      if( Character.toString(current).equals(")") && !( Character.toString(next).equals(")") || Character.toString(next).equals("+") || Character.toString(next).equals("-") || Character.toString(next).equals("/") || Character.toString(next).equals("*") || Character.toString(next).equals(" ") || Character.toString(next).equals("^")) )
         ma_chaine+=Character.toString(current)+"*"; // si la condition est verai ajoutant (*)
      

                else
           ma_chaine+=Character.toString(current);

         }
       ma_chaine+=Character.toString(next);
          return ma_chaine ; 
  }


// traitement des espaces et []  !
public String with_out_spacing (String expression)
{
     int k=0,j=0;
    String my_expression="" ;
    char current;
    for(int i=0; i<expression.length();i++ ) {
        current = expression.charAt(i);
        if(Character.toString(current).equals(" "))
            continue;
        if(Character.toString(current).equals("[")) {
            k++;
            my_expression += "(";
            continue ;
        }
        if(Character.toString(current).equals("]")) {
            j++;
            my_expression += ")";
        }
        else
            my_expression +=Character.toString(current);
    }

  if (k!=j)
        return "ERROR";


    return my_expression;
}


 public String Extract_Interval (String expression)
 {
     char current= ' ';
     boolean vrf  = false ;
     String expression_with_out_interval="" ;
     for (int i=0 ; i<expression.length(); i++)
     {
         current=expression.charAt(i);

         if (Character.toString(current).equals(":"))
         {
             vrf = true ;

         }
         if (vrf == true )
             this.intervalle += Character.toString(current);
         else
             expression_with_out_interval+= Character.toString(current);

     }
     return expression_with_out_interval;
 }// de la methode Extract

    public LinkedList <String> interval_Analyser(String intervalle)
    {

        LinkedList<String> tokens = new LinkedList<>();

        char current;
        String buffer = new String("");
        String keys = new String(":" + "[" + "," + "]");

        for(int i = 0; i < intervalle.length(); i++)
        {
            current = intervalle.charAt(i);
            if (Character.toString(current).equals(" "))
                 continue;


            //Si le caractère courant est une clé, ajouter le buffer à la sortie, puis ajouter la clé
            if(keys.contains(Character.toString(current)))
            {
                if(buffer.length() > 0)
                    tokens.add(buffer.trim().toLowerCase());

                tokens.add(Character.toString(current));
                buffer = "";
            }

            //Sinon ajouter le caractère courant au buffer
            else
                buffer += Character.toString(current);

        }

        if(buffer.length() > 0)
            tokens.add(buffer.trim().toLowerCase());


        return tokens;
    }

    public boolean get_bornes  (LinkedList <String> intervalle)
    {
       if(intervalle.size()==0)
            return  false ;

           if (intervalle.get(0).equals(":") && intervalle.get(1).equals("[") && intervalle.get(3).equals(",") && intervalle.get(5).equals("]")) {
               try {

                   this.borne_inferieur = Double.parseDouble(intervalle.get(2));
                   this.borne_superieur = Double.parseDouble(intervalle.get(4));
                   if (borne_superieur - borne_inferieur <= 0)
                       return false;
                   else
                       return true;
               } catch (Exception e) {
                   return false;
               }
           }


            return false ;




    }


    public boolean Est_valide ()
    {
        return  get_bornes  (interval_Analyser(intervalle));
    }

}// fin de la class 
