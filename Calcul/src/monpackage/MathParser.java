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


// décomposer expression en tokens selon des (operateur, separateur , fonction , brackets )
 public  LinkedList<String> tokenizer(String expression)
    {
        LinkedList<String> tokens = new LinkedList<>();
     
        char current;
        String buffer = new String("");
        String keys = new String(operators + leftBracket + rightBracket + separator);
     
        for(int i = 0; i < expression.length(); i++)
        {
            current = expression.charAt(i);

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
   



   // application de  Shunting-yard algorithm expression normal vers expression polonaise inversé
 public  LinkedList<String> analyzer(LinkedList<String> tokens)
    {
        LinkedList<String> functionsList = new LinkedList<>(Arrays.asList(functions));
        LinkedList<String>    outputList = new LinkedList<>();
        LinkedList<String>         stack = new LinkedList<String>();
         
        for(int i = 0; i < tokens.size(); i++)
        {
            //Si le token courant est une fonction, l'ajouter à la pile
            if(functionsList.contains(tokens.get(i)))
                stack.add(tokens.get(i));
    
            //Sinon si le token est un séparateur d'arguments de fonction
            else if(separator.equals(tokens.get(i)))
            {
                try
                {
                    //Tant que le haut de la pile n'est pas une paranthèse gauche, le retirer et l'ajouter à la sortie
                    while(!leftBracket.equals(stack.getLast()))
                        outputList.add(stack.removeLast());
                }
                catch(NoSuchElementException exception)
                {
                    //Si la pile est vidée sans trouver de paranthèse gauche, le séparateur est placé hors de la fonction
                    System.out.println("ERROR : Misplaced comma (Use \".\" instead of \",\")");
                    return new LinkedList<>();
                }
            }
             
            //Sinon si le token est un opérateur
            else if(operators.contains(tokens.get(i)))
            {
                try
                {
                    while(operators.contains(stack.getLast()))
                    {
                        //Condition garantissant l'ordre des opérateurs selon leur priorité, dans la sortie
                        if( ( precedence[operators.indexOf(tokens.get(i))] <= precedence[operators.indexOf(stack.getLast())] &&  Character.toString(associativity.charAt(operators.indexOf(tokens.get(i)))).equals("l") )
                        ||  ( precedence[operators.indexOf(tokens.get(i))] <  precedence[operators.indexOf(stack.getLast())] &&  Character.toString(associativity.charAt(operators.indexOf(tokens.get(i)))).equals("r") ) )
                            outputList.add(stack.removeLast());
                        else
                            break;
                    }
                }
                catch(NoSuchElementException exception){}
                 
                //Ajout de l'opérateur dans la pile
                stack.add(tokens.get(i));
            }
             
            //Sinon si le token est une paranthèse gauche, l'ajouter à la pile
            else if(leftBracket.equals(tokens.get(i)))
                stack.add(tokens.get(i));
             
            //Sinon si le token est une paranthèse droite, vider la pile jusqu'à trouver une paranthèse gauche, la dépiler aussi
            else if(rightBracket.equals(tokens.get(i)))
            {
                try
                {
                    while(!leftBracket.equals(stack.getLast()))
                        outputList.add(stack.removeLast());
                         
                    stack.removeLast();
                     
                    //Si le haut de la pile est une fonction, la dépiler aussi
                    if(stack.size() > 0)
                        if(functionsList.contains(stack.getLast()))
                            outputList.add(stack.removeLast());
                }
                catch(NoSuchElementException exception)
                {
                    //Si la paranthèse gauche n'est pas trouvée, il y a un mauvais paranthèsage
                    System.out.println("ERROR : Unbalanced expression");
                    return new LinkedList<>();
                }
            }
             
            //Sinon ajouter le token à la sortie
            else
                outputList.add(tokens.get(i));
            
              
                   
        }
         
        //Si la pile n'est pas vide, la dépiler vers la sortie
        while(stack.size() > 0)
        {
            if(leftBracket.equals(stack.getLast()))
            {
                //Si une paranthèse gauche est trouvée, il y a un mauvais paranthèsage
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
