/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import GUI.Mapa;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logika.*;
import uiText.TextoveRozhrani;

/**
 *
 * @author Martin
 */
public class Main extends Application {
    
    private TextArea centralText;
    private IHra hra;
    private TextField zadejPrikazTextArea;
    
    private Mapa mapa;
    
    
    @Override
    public void start(Stage primaryStage) {
        hra = new Hra();
        
        mapa = new Mapa(Hra);
        BorderPane borderPane = new BorderPane();
         
            
        centralText = new TextArea();
        centralText.setText(hra.vratUvitani());
        centralText.setEditable(false);
        borderPane.setCenter(centralText);
        
        Label zadejPrikazLabel = new Label("Zadej prikaz: ");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        zadejPrikazTextArea = new TextField("...");
        zadejPrikazTextArea.setOnAction((ActionEvent event) -> {
            String vstupniPrikaz = zadejPrikazTextArea.getText();
            String odpovedHry = hra.zpracujPrikaz(vstupniPrikaz);
            
            centralText.appendText("\n" + vstupniPrikaz + "\n");
            centralText.appendText("\n" + odpovedHry + "\n");
            
            zadejPrikazTextArea.setText("");
            
            if(hra.konecHry()){
                zadejPrikazTextArea.setEditable(false);
                centralText.appendText(hra.vratEpilog());
            }
        });
    
        FlowPane obrazekFlowPane = new FlowPane();
        obrazekFlowPane.setPrefSize(200, 200);
        ImageView obrazekImageView = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa.jpg"),200,200,false,true));
        
        Circle tecka = new Circle(10, Paint.valueOf("red"));
        
        obrazekFlowPane.setAlignment(Pos.CENTER);
        obrazekFlowPane.getChildren().add(obrazekImageView);
    
        
        
        FlowPane dolniLista = new FlowPane();
        dolniLista.setAlignment(Pos.CENTER);
        dolniLista.getChildren().addAll(zadejPrikazLabel,zadejPrikazTextArea);
       
        
        borderPane.setLeft(mapa);
        borderPane.setBottom(dolniLista);
        Scene scene = new Scene(borderPane, 650, 350);
        primaryStage.setTitle("Adventura!");
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
        zadejPrikazTextArea.requestFocus();
    }
    
    private AnchorPane nastaveniMapy(){
        AnchorPane obrazekPane = new AnchorPane();
        
        ImageView obrazekImageView = new ImageView(new Image(Main.class.getResourceAsStream("/zdroje/mapa.jpg"),200,200,false,true));
        
        Circle tecka = new Circle(10, Paint.valueOf("red"));
        
        obrazekFlowPane.setTopAnchor(tecka, 25.0);
        obrazekFlowPane.setLeftAnchor(tecka, 100.0);
            
        obrazekFlowPane.getChildren().addAll(obrazekImageView, tecka);
        
        return obrazekPane;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0){
        launch(args);
        }
        else {
            if (args[0].equals(".txt")) {
            IHra hra = new Hra();
            TextoveRozhrani ui = new TextoveRozhrani(hra);
            ui.hraj();
           }
            else{
                   System.out.println("Neplatny parametr");
                    System.exit(1);
                    }
        }
    }
    
}