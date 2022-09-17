package agents;
//COMPRADOR
import gui.BookBuyerGui;
//Para hacer referencia a BookBuyerGui
//Importaciones de jade que se necesitan
import jade.core.Agent;
import behaviours.RequestPerformer;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BookBuyerAgent extends Agent {
  private String bookTitle; //Para el título del libro
  private AID[] sellerAgents; //Para almacenar los agentes vendedores
  private int ticker_timer = 10000; //Tiempo de respuesta
  private BookBuyerAgent this_agent = this;
  private BookBuyerGui gui; //Para poder manipular a BookBuyerGui
  
  protected void setup() 
  {
    System.out.println("Buyer agent " + getAID().getName() + " is ready");
    gui = new BookBuyerGui(this);
    gui.showGui();
  }
  public void proceso()
  {
    Object[] args = getArguments();
    if(bookTitle.length() > 0) 
    { //Si escribió algo
      //bookTitle = (String)args[0];
      System.out.println("Book: " + bookTitle);

      addBehaviour(new TickerBehaviour(this, ticker_timer) 
      {
        protected void onTick() 
        {
          System.out.println("Trying to buy " + bookTitle);

          DFAgentDescription template = new DFAgentDescription();
          ServiceDescription sd = new ServiceDescription();
          sd.setType("book-selling");
          template.addServices(sd);

          try 
          {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            System.out.println("Found the following seller agents:");
            sellerAgents = new AID[result.length];
            for(int i = 0; i < result.length; i++) 
            {
              sellerAgents[i] = result[i].getName();
              System.out.println(sellerAgents[i].getName());
            }

          }catch(FIPAException fe) {
            fe.printStackTrace();
          }

          myAgent.addBehaviour(new RequestPerformer(this_agent,gui));
        }
      });
    } else 
    {
      System.out.println("No target book title specified");
      doDelete();
    }
  } 
  protected void takeDown() {
    System.out.println("Buyer agent " + getAID().getName() + " terminating");//Aqui termina 
    gui.dispose();
  } 
  public void setBookTitle(String bookTitle){
      this.bookTitle = bookTitle;
  } 
  public AID[] getSellerAgents() {
    return sellerAgents;
  } 
  public String getBookTitle() {
    return bookTitle;
  }
}