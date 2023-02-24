package monpackage;

import java.util.Arrays;
import java.util.LinkedList;

public class Calculator extends MathParser implements Interpretable {
    
    LinkedList <String> reverse_tokens =  new LinkedList<>();

    public Calculator(String expression)
    {

        super(expression);
        this.reverse_tokens= super.final_list();

    }

    public  double calculette (double x_value) throws Exception
    {
        LinkedList<String> functionsList = new LinkedList<String>(Arrays.asList(functions)); // la liste des fonctions
        LinkedList<Double> stack_value= new LinkedList<>(); // une linkedlist qui va jouer le role d'une pile (empiler / depiler) pour evaluer l'expression generer par analyzer


        // parcourir la liste des tokens inversé et  interprété chaque tokens en empilant ou dépilant la pile
        for (int i = 0 ;  i<reverse_tokens.size();i++)
        {
            // si le touken est une variable (x)
            if(variable.contains(reverse_tokens.get(i)))
            {
           // remplacer le x-> la valeur passer en paramettre et empiler dans une pile
                stack_value.addFirst(x_value);
            }
           else if(is_numbre(reverse_tokens.get(i)))
            {
                // si le token est un nombre alors le caster et l'empiler dans la pile
                stack_value.addFirst(Double.parseDouble(reverse_tokens.get(i)));
            }
            // si le tokens est une fonction arité 1
            else if(functionsList.contains(reverse_tokens.get(i)))
            {
                // dépile la pile et passe en paramètres de la fonction  la valeur dépiler

                double y = stack_value.get(0);
                // supprimer la valeur dépiler de la pile ( pas besoin )
                stack_value.removeFirst();
                // stoker la valeur calculer par la fonction dans la pile
                stack_value.addFirst(function_evaluater(reverse_tokens.get(i),y));


            }
            // si le touken est un opérateur
            else if(operators.contains(reverse_tokens.get(i)))
            {
                // depiler 2 fois et chaque fois on supprime
                double y = stack_value.getFirst();
                stack_value.removeFirst();

                double x=stack_value.getFirst();
                stack_value.removeFirst();

                stack_value.addFirst(operators_evauater(reverse_tokens.get(i),y,x));
            }
            else
            {
               throw new Exception();

            }

        }

    return  stack_value.getFirst();


    }// fin de la methode calculette



@Override
    public double function_evaluater (String function, double value )
    {

        if(function.equals(functions[0]))
            return(Math.log(value));//ln

        if(function.equals(functions[1]))
            return (Math.sin(value));

        if(function.equals(functions[2]))
            return (Math.cos(value));

        if(function.equals(functions[3]))
            return(Math.tan(value));


        if(function.equals(functions[4]))
            return(Math.asin(value));


        if(function.equals(functions[5]))
            return(Math.acos(value));

        if(function.equals(functions[6]))
            return (Math.atan(value));

        if(function.equals(functions[7]))
            return(Math.exp(value));


        if(function.equals(functions[8]))
            return(Math.abs(value));

        if(function.equals(functions[9]))
            return(Math.sqrt(value));


        if(function.equals(functions[10]))
            return(Math.sinh(value));

        if(function.equals(functions[11]))
            return(Math.cosh(value));

        if(function.equals(functions[12]))
            return(Math.tanh(value));

        if(function.equals(functions[13]))
            return (Math.log(value+Math.sqrt( (value*value)-1)));// ARCCOSINUS HYPERBOLIQUE

        if(function.equals(functions[14])) // ARCSINUS HYPERBOLIQUE
            return(Math.log(value+Math.sqrt( (value*value)+1)));

        if(function.equals(functions[15]))  // ARCTANHYPERBOLIQUE
            return( 0.5 * (Math.log((1+value)/(1-value))));

         return 0;

    }


    @Override
    public double operators_evauater (String operator,  double value , double value1)
    {
        if(operator.equals("+"))
         return value+value1;

        if(operator.equals("-"))
            return value1-value;

        if(operator.equals("*"))
            return value*value1;

        if(operator.equals("/"))
            return (double)value1/value;

        if(operator.equals("^"))
            return (Math.pow(value1,value));
        
         return 0;
    }

}
/*

 */