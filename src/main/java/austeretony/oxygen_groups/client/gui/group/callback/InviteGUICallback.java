package austeretony.oxygen_groups.client.gui.group.callback;

import austeretony.alternateui.screen.button.GUIButton;
import austeretony.alternateui.screen.callback.AbstractGUICallback;
import austeretony.alternateui.screen.core.AbstractGUISection;
import austeretony.alternateui.screen.core.GUIBaseElement;
import austeretony.alternateui.screen.text.GUITextField;
import austeretony.alternateui.screen.text.GUITextLabel;
import austeretony.oxygen.client.api.OxygenHelperClient;
import austeretony.oxygen.client.core.api.ClientReference;
import austeretony.oxygen.client.gui.settings.GUISettings;
import austeretony.oxygen.common.main.OxygenSoundEffects;
import austeretony.oxygen_groups.client.GroupsManagerClient;
import austeretony.oxygen_groups.client.gui.group.GroupGUISection;
import austeretony.oxygen_groups.client.gui.group.GroupMenuGUIScreen;

public class InviteGUICallback extends AbstractGUICallback {

    private final GroupMenuGUIScreen screen;

    private final GroupGUISection section;

    private GUITextField usernameField;

    private GUITextLabel playerStatusLabel;

    private GUIButton confirmButton, cancelButton;

    private final String 
    playerFoundStr = ClientReference.localize("oxygen.gui.playerFound"),
    playerNotFoundStr = ClientReference.localize("oxygen.gui.playerNotFound");

    public InviteGUICallback(GroupMenuGUIScreen screen, GroupGUISection section, int width, int height) {
        super(screen, section, width, height);
        this.screen = screen;
        this.section = section;
    }

    @Override
    public void init() {
        this.addElement(new InviteCallbackGUIFiller(0, 0, this.getWidth(), this.getHeight()));
        this.addElement(new GUITextLabel(2, 2).setDisplayText(ClientReference.localize("oxygen_groups.gui.invitePlayerCallback"), true, GUISettings.instance().getTitleScale()));   
        this.addElement(new GUITextLabel(2, 16).setDisplayText(ClientReference.localize("oxygen_groups.gui.invitePlayerCallback.request"), false, GUISettings.instance().getTextScale()));  
        this.addElement(new GUITextLabel(2, 26).setDisplayText(ClientReference.localize("oxygen.gui.username"), false, GUISettings.instance().getSubTextScale()));  
        this.addElement(this.usernameField = new GUITextField(2, 35, 136, 9, 24).setTextScale(GUISettings.instance().getSubTextScale())
                .enableDynamicBackground(GUISettings.instance().getEnabledTextFieldColor(), GUISettings.instance().getDisabledTextFieldColor(), GUISettings.instance().getHoveredTextFieldColor())
                .setLineOffset(3).cancelDraggedElementLogic());       
        this.addElement(this.playerStatusLabel = new GUITextLabel(2, 43).setTextScale(GUISettings.instance().getSubTextScale()).disableFull());    

        this.addElement(this.confirmButton = new GUIButton(15, this.getHeight() - 12, 40, 10).setSound(OxygenSoundEffects.BUTTON_CLICK.soundEvent).enableDynamicBackground().setDisplayText(ClientReference.localize("oxygen.gui.confirmButton"), true, GUISettings.instance().getButtonTextScale()));
        this.addElement(this.cancelButton = new GUIButton(this.getWidth() - 55, this.getHeight() - 12, 40, 10).setSound(OxygenSoundEffects.BUTTON_CLICK.soundEvent).enableDynamicBackground().setDisplayText(ClientReference.localize("oxygen.gui.cancelButton"), true, GUISettings.instance().getButtonTextScale()));

        this.confirmButton.disable();
    }

    @Override
    protected void onClose() {
        this.usernameField.reset();
        this.playerStatusLabel.disableFull();
        this.confirmButton.disable();
    }

    @Override
    public boolean keyTyped(char typedChar, int keyCode) {
        boolean flag = super.keyTyped(typedChar, keyCode);   
        if (this.usernameField.isDragged()) {
            if (!this.usernameField.getTypedText().isEmpty()) {
                this.playerStatusLabel.enableFull();
                if (GroupsManagerClient.isPlayerAvailable(this.usernameField.getTypedText())) {
                    this.playerStatusLabel.setDisplayText(this.playerFoundStr);
                    this.confirmButton.enable();
                } else {
                    this.playerStatusLabel.setDisplayText(this.playerNotFoundStr);
                    this.confirmButton.disable();
                }
            } else {
                this.playerStatusLabel.disableFull();
                this.confirmButton.disable();
            }
        }
        return flag;   
    }

    @Override
    public void handleElementClick(AbstractGUISection section, GUIBaseElement element, int mouseButton) {
        if (mouseButton == 0) {
            if (element == this.cancelButton)
                this.close();
            else if (element == this.confirmButton) {
                if (GroupsManagerClient.isPlayerAvailable(this.usernameField.getTypedText())) 
                    GroupsManagerClient.instance().inviteToGroupSynced(OxygenHelperClient.getSharedPlayerData(this.usernameField.getTypedText()).getIndex());
                this.close();
            }
        }
    }
}
