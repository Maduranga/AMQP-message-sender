import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.StyledText;


public class SendingGUI implements SelectionListener {
	
	private Display display;
	private Shell shlSenderWindow;
	private Text senderNameText;
	private Text recepientNameText;
	private Text messageIDText;
	private Button sendButton;
	private Button closeButton;
	private List exchangesList;
	private List queuesList;
	private StyledText ticketText;
	
	private boolean routingKeyEnabled = false;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SendingGUI window = new SendingGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		shlSenderWindow = new Shell();
		shlSenderWindow.setSize(373, 578);
		shlSenderWindow.setText("Sender Window");
		
		Label senderNameLabel = new Label(shlSenderWindow, SWT.NONE);
		senderNameLabel.setBounds(23, 13, 111, 17);
		senderNameLabel.setText("Sender Name");
		
		senderNameText = new Text(shlSenderWindow, SWT.BORDER);
		senderNameText.setBounds(155, 13, 183, 27);
		
		Label recepientNameLabel = new Label(shlSenderWindow, SWT.NONE);
		recepientNameLabel.setBounds(23, 50, 111, 17);
		recepientNameLabel.setText("Recepient Name");
		
		recepientNameText = new Text(shlSenderWindow, SWT.BORDER);
		recepientNameText.setBounds(155, 46, 183, 27);
		
		Label TicketIdLabel = new Label(shlSenderWindow, SWT.NONE);
		TicketIdLabel.setBounds(23, 89, 111, 17);
		TicketIdLabel.setText("Ticket ID");
		
		messageIDText = new Text(shlSenderWindow, SWT.BORDER);
		messageIDText.setBounds(155, 79, 183, 27);
		
		Label ticketLabel = new Label(shlSenderWindow, SWT.NONE);
		ticketLabel.setBounds(23, 130, 70, 17);
		ticketLabel.setText("Ticket");
		
		Label exchangesLabel = new Label(shlSenderWindow, SWT.NONE);
		exchangesLabel.setBounds(52, 363, 82, 17);
		exchangesLabel.setText("Exchanges");
		
		exchangesList = new List(shlSenderWindow, SWT.V_SCROLL|SWT.H_SCROLL);
		exchangesList.setBounds(38, 386, 111, 81);
		
		sendButton = new Button(shlSenderWindow, SWT.NONE);
		sendButton.setBounds(44, 510, 105, 29);
		sendButton.setText("Send Ticket");
		sendButton.addSelectionListener(this);
		
		ticketText = new StyledText(shlSenderWindow, SWT.BORDER);
		ticketText.setBounds(10, 153, 347, 183);
		
		closeButton = new Button(shlSenderWindow, SWT.NONE);
		closeButton.setBounds(208, 510, 105, 29);
		closeButton.setText("Close");
		closeButton.addSelectionListener(this);
		
		queuesList = new List(shlSenderWindow, SWT.V_SCROLL|SWT.H_SCROLL);
		queuesList.setBounds(208, 386, 111, 81);

		Label queuesLabel = new Label(shlSenderWindow, SWT.NONE);
		queuesLabel.setBounds(234, 363, 70, 17);
		queuesLabel.setText("Queues");

		
		UpdateExchangeList(MessageSenderHighlevelClass.GetExchangeList(), MessageSenderHighlevelClass.GetQueueList());
		
		shlSenderWindow.open();
		shlSenderWindow.layout();
		while (!shlSenderWindow.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}


	public void UpdateExchangeList(java.util.List <Exchange> ExchangeList, java.util.List <Queue> QueueList)
	{

		exchangesList.removeAll();
		queuesList.removeAll();
		
		for(int idx=0; idx<ExchangeList.size(); ++idx)
		{
			String exchName = ExchangeList.get(idx).toString();
	
			exchangesList.add(exchName);
		}	
		
		for(int idx=0; idx<QueueList.size(); ++idx)
		{
			String exchName = QueueList.get(idx).toString();
	
			queuesList.add(exchName);
		}	
	}
	
	public void CloseWindow()
	{
		display.close();
	}
	
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

	@Override
	public void widgetSelected(SelectionEvent e) {
		
		if(sendButton.isFocusControl())
		{
			System.out.println("Pressed Send button");
			if(exchangesList.getSelectionIndex()>=0 && queuesList.getSelectionIndex()>=0)
				MessageSenderHighlevelClass.BuildMessage(senderNameText.getText(), recepientNameText.getText(), messageIDText.getText(), ticketText.getText(), exchangesList.getItem(exchangesList.getSelectionIndex()), queuesList.getItem(queuesList.getSelectionIndex()));
			else
				JOptionPane.showMessageDialog(null,"Select an exchange and a queue.", "Inane error", JOptionPane.ERROR_MESSAGE);
		}
		else if(closeButton.isFocusControl())
		{
			MessageSenderHighlevelClass.CloseApplication();
		}
	}
}
