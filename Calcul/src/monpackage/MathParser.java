package monpackage;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MathParser extends Correction {

	/// initialisation des composants d'une expression mathematique 
    String valid_expression; 

	public MathParser (String expression  )
	{

      super(expression); 

       this.valid_expression=super.final_expression_formula();

	}


// d�composer expression en tokens selon des (operateur, separateur , fonction , brackets )
 public  LinkedList<String> tokenizer(String expression)
    {
        LinkedList<String> tokens = new LinkedList<>();
     
        char current;
        String buffer = new String("");
        String keys = new String(operators + leftBracket + rightBracket + separator);
     
        for(int i = 0; i < expression.length(); i++)
        {
            current = expression.charAt(i);

            //Si le caract�re courant est une cl�, ajouter le buffer � la sortie, puis ajouter la cl�
            if(keys.contains(Character.toString(current)))
            {
                if(buffer.length() > 0)
                    tokens.add(buffer.trim().toLowerCase());
                     
                tokens.add(Character.toString(current));
                buffer = "";
            }
             
            //Sinon ajouter le caract�re courant au buffer
            else
                buffer += Character.toString(current);
            
        }
         
        if(buffer.length() > 0)
            tokens.add(buffer.trim().toLowerCase());


        return tokens;
    }
   



   // application de  Shunting-yard algorithm expression normal vers expression polonaise invers�
 public  LinkedList<String> analyzer(LinkedList<String> tokens)
    {
        LinkedList<String> functionsList = new LinkedList<>(Arrays.asList(functions));
        LinkedList<String>    outputList = new LinkedList<>();
        LinkedList<String>         stack = new LinkedList<String>();
         
        for(int i = 0; i < tokens.size(); i++)
        {
            //Si le token courant est une fonction, l'ajouter � la pile
            if(functionsList.contains(tokens.get(i)))
                stack.add(tokens.get(i));
    
            //Sinon si le token est un s�parateur d'arguments de fonction
            else if(separator.equals(tokens.get(i)))
            {
                try
                {
                    //Tant que le haut de la pile n'est pas une paranth�se gauche, le retirer et l'ajouter � la sortie
                    while(!leftBracket.equals(stack.getLast()))
                        outputList.add(stack.removeLast());
                }
                catch(NoSuchElementException exception)
                {
                    //Si la pile est vid�e sans trouver de paranth�se gauche, le s�parateur est plac� hors de la fonction
                    System.out.println("ERROR : Misplaced comma (Use \".\" instead of \",\")");
                    return new LinkedList<>();
                }
            }
             
            //Sinon si le token est un op�rateur
            else if(operators.contains(tokens.get(i)))
            {
                try
                {
                    while(operators.contains(stack.getLast()))
                    {
                        //Condition garantissant l'ordre des op�rateurs selon leur priorit�, dans la sortie
                        if( ( precedence[operators.indexOf(tokens.get(i))] <= precedence[operators.indexOf(stack.getLast())] &&  Character.toString(associativity.charAt(operators.indexOf(tokens.get(i)))).equals("l") )
                        ||  ( precedence[operators.indexOf(tokens.get(i))] <  precedence[operators.indexOf(stack.getLast())] &&  Character.toString(associativity.charAt(operators.indexOf(tokens.get(i)))).equals("r") ) )
                            outputList.add(stack.removeLast());
                        else
                            break;
                    }
                }
                catch(NoSuchElementException exception){}
                 
                //Ajout de l'op�rateur dans la pile
                stack.add(tokens.get(i));
            }
             
            //Sinon si le token est une paranth�se gauche, l'ajouter � la pile
            else if(leftBracket.equals(tokens.get(i)))
                stack.add(tokens.get(i));
             
            //Sinon si le token est une paranth�se droite, vider la pile jusqu'� trouver une paranth�se gauche, la d�piler aussi
            else if(rightBracket.equals(tokens.get(i)))
            {
                try
                {
                    while(!leftBracket.equals(stack.getLast()))
                        outputList.add(stack.removeLast());
                         
                    stack.removeLast();
                     
                    //Si le haut de la pile est une fonction, la d�piler aussi
                    if(stack.size() > 0)
                        if(functionsList.contains(stack.getLast()))
                            outputList.add(stack.removeLast());
                }
                catch(NoSuchElementException exception)
                {
                    //Si la paranth�se gauche n'est pas trouv�e, il y a un mauvais paranth�sage
                    System.out.println("ERROR : Unbalanced expression");
                    return new LinkedList<>();
                }
            }
             
            //Sinon ajouter le token � la sortie
            else
                outputList.add(tokens.get(i));
            
              
                   
        }
         
        //Si la pile n'est pas vide, la d�piler vers la sortie
        while(stack.size() > 0)
        {
            if(leftBracket.equals(stack.getLast()))
            {
                //Si une paranth�se gauche est trouv�e, il y a un mauvais paranth�sage
                System.out.println("ERROR : Unbalanced expression");
                return new LinkedList<>();
            }
             
            outputList.add(stack.removeLast());
        }
      
       

        return outputList;


    }




    public LinkedList<String> final_list()

    {

        return analyzer(tokenizer(this.valid_expression));

    }

}// fin de la class
