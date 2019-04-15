package nl.avans.sagrada.model;

public class Invite {
    private Player sendedPlayer;
    private Account invitedAccount;
    private Game game;
    private boolean accepted;
    private boolean denied;
    
    public Invite() {
        accepted = false;
        denied = false;
    }
    
    public void setSendedPlayer(Player player) {
        sendedPlayer = player;
    }
    public void setAccount(Account account) {
        invitedAccount = account;
    }
    public void setInvitedAccount(Account account) {
        invitedAccount = account;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public void acceptInvite() {
        accepted = true;
        denied = false;
    }
    public void denyInvite() {
        accepted = false;
        denied = true; 
    }
    public boolean isPending() {
        if (accepted == false && denied == false) {
            return true;
        }
        else {
            return false;
        }
    }
}
