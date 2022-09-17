package behaviours;

import agents.BookSellerAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import gui.BookSellerGui;

public class PurchaseOrderServer extends CyclicBehaviour{ 
  BookSellerAgent bsAgent;
  BookSellerGui gui;
  
  public PurchaseOrderServer(BookSellerAgent a, BookSellerGui g) {
    bsAgent = a;
    gui = g;
  } 
  public void action() {
    MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
    ACLMessage msg = bsAgent.receive(mt);
    
    if(msg != null) {
      String title = msg.getContent();
      ACLMessage reply = msg.createReply();
      
      Integer price = (Integer)bsAgent.getCatalogue().remove(title);
      if(price != null) {
        reply.setPerformative(ACLMessage.INFORM);
        //Para que mande a la ventana del vendedor qué comprador lo compró
        String quien = title + " sold to agent " + msg.getSender().getName();
        //para que mande a buyer la cadena con el nombre del libro y el agente comprador
        gui.buyer(quien); 
        //impresión por consola para confirmar
        System.out.println(quien); 
      } else {
        reply.setPerformative(ACLMessage.FAILURE);
        reply.setContent("not-available");
      }
      bsAgent.send(reply);
    } else {
      block();
    }
  }
}
