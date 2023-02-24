package monpackage;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.LinkedList;


public class Main extends Application {
    // initialiation des variables
	 static TextField  texte = new TextField();
     public  Button tracer =  new Button(" Trace ");
     public Button effacer  = new  Button(" Remove  ");
     LinkedList<VBox> function_list = new LinkedList<>();

     Button add_functon  =  new Button("Ajouter Fonction");
     public Button zoomermoins =  new Button(" Zoomer - ");
     public Button zoomerplus =  new Button("Zommer +");
     static NumberAxis xAxis = new NumberAxis(-10,10,2);
     static NumberAxis yAxis= new NumberAxis(-10,10,2);

     static LineChart<Number,Number> lineChart= new LineChart<Number,Number>(xAxis,yAxis);

     LinkedList <XYChart.Series> liste = new LinkedList<>();


     XYChart.Series series2 = new XYChart.Series();
     public Calculator  obj ;

     public  int nbr_zommer=0;
   
   
   
   
   
   
    static int size=4;
  	MyStack stack =new MyStack(size);
  	
  	//fonction pour return texte dans textefield
  	public static class StringFonction{
  	  private final SimpleStringProperty nom;
  	  
  	  private  StringFonction(String nom) {
  		  this.nom= new SimpleStringProperty(nom);
  	  }
  	  
  	  public String getNom() {
 		     return  nom.get();
 	      }
  	  
  	  
  	  public void setNom(String mNom) {
  		   nom.set(mNom);
  	  }
  	  
    }
    
    
  //Classe contenant les items   
  static class XCell extends ListCell<String> {
  	
      HBox hbox = new HBox();
     
      Label label = new Label("Vide");
      Pane pane = new Pane();
      Button supprime = new Button("X");
      
      

      String lastItem;
      
      
      

      public XCell() {
          super();
          supprime.setStyle("-fx-background-color: red");
          supprime.setFont(new Font("Arial Black", 12.5));
          supprime.setPadding(new Insets(6,10,6,10));
          hbox.getChildren().addAll(label, pane, supprime);
          pane.setPadding(new Insets(15,0,10,0));
          HBox.setHgrow(pane, Priority.ALWAYS);
          supprime.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
             	
             	getListView().getItems().remove(getIndex());
             	setText(null);
             	
                 	if (!lineChart.getData().isEmpty()){
                 		
                		 
                		 if(selectedIndex>=0) {
                			lineChart.getData().remove(getIndex()+selectedIndex);
                          	
                          	if(selectedIndex>=0) { 
                     			lineChart.getData().remove(getIndex()+selectedIndex);
                     		   
                             }	
                          }
                		 
                		 else{
                  			 
                   			lineChart.getData().remove(getIndex()+selectedIndex);
                   			
                          }
                		 
                		 
                       		 
                 	}
               
                 	
                 
          }});
      }
      
      // Charge les items(bouton,texte,...)
      @Override
     protected void updateItem(String item, boolean empty) {
    	  //Super c'est pour gerer l'ajout des items
          super.updateItem(item, empty);
          setText(null);  // initialisation à nulle si il y'a pas de texte
          if (empty) {
              lastItem = null;
              setGraphic(null);
          } else {
         	
              lastItem = item;
              label.setText(item!=null ? item : "<null>");
              setGraphic(hbox);
              
             
          }   
          
          
      }
  }








  private final static ObservableList<String>  stringfonction =
  		FXCollections.observableArrayList(
  				
  				);

  //initialisation à -1 puisque ça concerne un tableau 
  private static int selectedIndex=-1;


    // le programme
/********************************************************************************/

    @Override

    public void start(Stage frame) throws Exception {
    	
    	
    	
    	
    	Group root1 = new Group();
    	Label nameLabel = new Label("");
    	nameLabel.setFont(new Font("Arial black", 15));
    
    	
		nameLabel.setPadding(new Insets(15,0,10,0));
		nameLabel.setStyle("-fx-background-color: #ffd8c4;");
		
		
		
		ListView<String> maliste = new ListView();
		maliste.setItems(stringfonction);
		
		
		
		maliste.setStyle("-fx-font-size: 20px; -fx-font-family: 'SketchFlow Print';");
		maliste.setStyle("-fx-background-color: #f6e5d3; -fx-text-fill: bisque;-fx-control-inner-background: bisque;");
		//le smaliste.fixedCellSizeProperty();
		maliste.setMaxSize(403, 150);
		
	    
		 root1.getChildren().add(maliste);
		 maliste.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
	        	
	            @Override
	            public ListCell<String> call(ListView<String> param) {
	            	
	                return new XCell();
	            }
	        	
	        });
		
		maliste.setOnMouseClicked(event ->{
			
		try {
					String selectedItem = maliste.getSelectionModel().getSelectedItem().toString();
				    selectedIndex = maliste.getSelectionModel().getSelectedIndex();
				    texte.setText(selectedItem);
				    maliste.setStyle("-fx-font-size: 20px; -fx-font-family: 'SketchFlow Print';-fx-background-color: #f6e5d3;");
		            
		}
		catch (Exception e)
	                {
	                	 System.out.println("Aucune expression selectionnée");

	                }
	               
			    
	});
		
	add_functon.setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent actionEvent) { 
	             if(!texte.getText().isEmpty()) {
	            	if(stack.getTop()!=size-1) {
	        			stack.Push( stringfonction.add(new String(texte.getText())));
	        			
	        			}
	            	      maliste.setStyle("-fx-background-color: white; -fx-text-fill: bisque;-fx-control-inner-background: bisque ;");
	             }
	            	      
	            }});
	        
   

        tracer.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {


                try {
                	Calculator pr = new Calculator(String.valueOf(texte.getText ()));
                    obj=getCalculator(pr);
                    double inferieur = pr.borne_inferieur;
                    double superieur = pr.borne_superieur;
                    liste.add(new XYChart.Series());
                    int flag = 0;
                    int flage_2 = 0;
                    if (pr.Est_valide() == true) {
                    // if (inferieur <= -40 || superieur > 40 )
                           //System.exit(-1);
                        for (double  x=inferieur ; x<=superieur; x+=0.01)
                        {

                            if (pr.calculette(x)<=10 && pr.calculette(x)>=-10 )
                            {
                            	 if (flag==0) {
                                     liste.add(new XYChart.Series());
                                     flag=1 ;
                                 }
                               liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                            }

                            else
                            {
                            	 if (flag==1)
                                 {
                                     liste.add(new XYChart.Series());
                                     liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                                 }
                                 flag=0;

                            }


                        }


                        for (XYChart.Series element : liste)
                        lineChart.getData().addAll(element);

                        liste.clear();


                    } // fin de la condition intervalle

                 else{


                        to_trace (pr, 10, -10);

                       

                    }


                } catch (Exception e)
                {
                    System.out.println("ERROR ! : INVALID SANTAXE "+e.getMessage()) ;

                }



            }});




        zoomermoins.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	
            try {
                setzoomerplus (obj );
                
            } catch (Exception e)
                {
                    System.out.println("Aucune Courbe à zoomer ") ;

                }
            }
            
           

        });


        zoomerplus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	 
                    if(!lineChart.getData().isEmpty()) {
                          setZoomermoins();}

            	
                    else
                        System.out.println("Aucune Courbe à zoomer ") ;

                    
            }/// action hendler
        });




        
        
        frame.setTitle("javaFX");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1100, 700);


        root.setPadding(new Insets(15,15,15,15));
        texte.setStyle("-fx-text-inner-color: red;");
        texte.setStyle("-fx-text-fill: red; -fx-font-size: 18.5px;");
        HBox bo =  new HBox(7);
        //bo.getChildren().add(add_functon);
        bo.getChildren().add(zoomermoins);
        bo.getChildren().add(zoomerplus);
        bo.setStyle("-fx-background-color: #ffd8c4;");
        zoomermoins.setPadding(new Insets(7,17.5,7,17.5));
        zoomerplus.setPadding(new Insets(7,17.5,7,17.5));
        zoomerplus.setFont(new Font("Arial Black", 15));
        zoomermoins.setFont(new Font("Arial Black", 15));  
        zoomerplus.setStyle("-fx-background-color: dodgerblue;");
        zoomermoins.setStyle("-fx-background-color: dodgerblue");
        
    
        bo.setPadding(new Insets(10,10,0,10));
        
        HBox myHBox = new HBox(5);
		myHBox.setPadding(new Insets(0,0,0,0));
		myHBox.getChildren().addAll(tracer,add_functon);
		
		
        
        VBox myVBox = new VBox(10);
        myVBox.setStyle("-fx-background-color: #f6e5d3;");
        myVBox.setPadding(new Insets(0,0,0,0));
		myVBox.getChildren().addAll(texte,myHBox,nameLabel, maliste);
		add_functon.setPadding(new Insets(7,15,7,15));
		add_functon.setStyle("-fx-background-color: red;");
		add_functon.setFont(new Font("Arial Black", 12));  
		tracer.setPadding(new Insets(7,31,7,31));
		tracer.setFont(new Font("Arial Black", 12));  
		
		


		
        root.setTop(bo);



       lineChart.setCreateSymbols(false);

        root.setBackground(new Background(new BackgroundFill(Color.BISQUE, null, null)));
       
       
       

        
        
        VBox st =  new VBox();
        st.setStyle("-fx-background-color: #ffd8c4;");
       
        st.setPadding(new Insets(10,10,10,5));
        texte.setPromptText("Mathematical Expression");
        texte.setPrefSize(220,55);
        st.getChildren().addAll(myVBox);
        lineChart.setStyle("-fx-background-color: #f0e4cf;");
       
        root.setLeft(st);

        lineChart.setLegendVisible(false);
        root.setCenter(lineChart);
       
        
        
        
        
        
      
        
        

        frame.setScene(scene);
        frame.show();

    }

        
        
   

    


// la fonction qui calcule la fonction et renvoie le resulatat de la fonction (double )

    public Calculator getCalculator(Calculator obj )
    {
        return obj ;
    }
    
    
    
    
    
    
    
    public void to_trace (Calculator pr, double borne_sup, double borne_inf) throws  Exception
    {
    	
    	 int flag = 0 ;
         int flage_2 = 0;
         int i =0;





                for (double x=-10; x<=10; x+=0.01) {

                    if (pr.calculette(x)<=100 && pr.calculette(x)>=-100)  {

                         if (flag==0) {
                             liste.add(new XYChart.Series());
                             flag=1 ;
                         }
                       liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                    }

                    else {
                        if (flag==1)
                        {
                            liste.add(new XYChart.Series());
                            liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                        }
                        flag=0;
                   }
                }

               

                for (XYChart.Series element : liste)
                lineChart.getData().addAll(element);
                liste.clear();
                
                
             
             
    	
    }
    
 


    public  void  setzoomerplus (Calculator  pr )
    {

        nbr_zommer++;
        double born_super = xAxis.getUpperBound() * 2;
        if(born_super<=40) {
            double born_inf = xAxis.getUpperBound();
            //double born_super = xAxis.getUpperBound() * 2;
            double p = born_super;
            double r = xAxis.getLowerBound() * 2;
            double v = xAxis.getTickUnit() * 2;
            double inferieur = pr.borne_inferieur;
            double superieur = pr.borne_superieur;
            int flag = 0;

            xAxis.setUpperBound(p);
            xAxis.setLowerBound(r);
            xAxis.setTickUnit(v);

            yAxis.setUpperBound(p);
            yAxis.setLowerBound(r);
            yAxis.setTickUnit(v);
            int flage_2=0;
            double pas = 0.01;
            try {
                if (pr.Est_valide() == true) {

                    for (double  x=inferieur ; x<=superieur; x+=0.01)
                    {

                        if (pr.calculette(x)<=10 && pr.calculette(x)>=-10 )
                        {
                            flage_2=0;
                            if(flag==0) {
                                liste.add(new XYChart.Series());
                                liste.getLast().getData().add(new XYChart.Data<Double, Double>(x-0.01, pr.calculette(x-0.01)));
                                flag=1;
                            }
                            liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                        }

                        else
                        {
                            flag =0 ;

                            if (flage_2==0)
                            {
                                liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                                flage_2=1;
                            }

                        }


                    }


                    for (XYChart.Series element : liste)
                        lineChart.getData().addAll(element);

                    liste.clear();

                     
                }

                    for (double x = born_super; x >= 0; x -= 0.01) {

                        if (pr.calculette(x) <= born_super) {

                            if (flag == 0) {
                                liste.add(new XYChart.Series());
                                flag = 1;
                            }
                            liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                        }
                        if (pr.calculette(x) >= -born_super) {
                            if (flag == 0) {
                                liste.add(new XYChart.Series());
                                flag = 1;
                            }
                            liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                        } else {
                            if (flag == 1) {
                                liste.add(new XYChart.Series());
                                liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                            }
                            flag = 0;
                        }
                    }


                    for (XYChart.Series element : liste)
                        lineChart.getData().addAll(element);

                    liste.clear();


                    liste.add(new XYChart.Series());
                    for (double x = 0; x >= -born_super; x -= 0.01) {

                        if (pr.calculette(x) <= born_super) {

                            if (flag == 0) {
                                liste.add(new XYChart.Series());
                                //   liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                                flag = 1;
                            }
                            liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                        }
                        if (pr.calculette(x) >= -born_super) {

                            if (flag == 0) {
                                liste.add(new XYChart.Series());
                                //   liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                                flag = 1;
                            }
                            liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                        } else {
                            if (flag == 1) {
                                liste.add(new XYChart.Series());
                                liste.getLast().getData().add(new XYChart.Data<Double, Double>(x, pr.calculette(x)));
                            }
                            flag = 0;
                        }

                    }


                for (XYChart.Series element : liste)
                    lineChart.getData().addAll(element);

                liste.clear();
                // lineChart.getData().addAll(liste.getLast());

            } catch (Exception e) {
                System.out.println("ERROR ! : INVALID SANTAXE " + e.getMessage());

            }
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
     

 public void setZoomermoins()
   {

      double p = xAxis.getUpperBound() / 2;
      double r = xAxis.getLowerBound() / 2;
      double v = xAxis.getTickUnit() / 2;

      xAxis.setUpperBound(p);
      xAxis.setLowerBound(r);
      xAxis.setTickUnit(v);

      yAxis.setUpperBound(p);
      yAxis.setLowerBound(r);
      yAxis.setTickUnit(v);
   }

    public static void main(String[] args) {


        launch(args);
    }


}
