package austeretony.oxygen_groups.client.gui.group.callback;

import austeretony.alternateui.screen.button.GUIButton;
import austeretony.alternateui.screen.callback.AbstractGUICallback;
import austeretony.alternateui.screen.core.AbstractGUISection;
import austeretony.alternateui.screen.core.GUIBaseElement;
import austeretony.alternateui.screen.text.GUITextLabel;
import austeretony.oxygen.client.core.api.ClientReference;
import austeretony.oxygen.client.gui.settings.GUISettings;
import austeretony.oxygen.common.main.OxygenSoundEffects;
import austeretony.oxygen_groups.client.GroupsManagerClient;
import austeretony.oxygen_groups.client.gui.group.GroupGUISection;
import austeretony.oxygen_groups.client.gui.group.GroupMenuGUIScreen;

public class ReadinessCheckGUICallback extends AbstractGUICallback {

    private final GroupMenuGUIScreen screen;

    private final GroupGUISection section;

    private GUIButton confirmButton, cancelButton;

    public ReadinessCheckGUICallback(GroupMenuGUIScreen screen, GroupGUISection section, int width, int height) {
        super(screen, section, width, height);
        this.screen = screen;
        this.section = section;
    }

    @Override
    public void init() {
        this.addElement(new ReadinessCheckCallbackGUIFiller(0, 0, this.getWidth(), this.getHeight()));
        this.addElement(new GUITextLabel(2, 2).setDisplayText(ClientReference.localize("oxygen_groups.gui.readinessCheckCallback"), true, GUISettings.instance().getTitleScale()));
        this.addElement(new GUITextLabel(2, 16).setDisplayText(ClientReference.localize("oxygen_groups.gui.readinessCheckCallback.request"), false, GUISettings.instance().getTextScale()));        

        this.addElement(this.confirmButton = new GUIButton(15, this.getHeight() - 12, 40, 10).setSound(OxygenSoundEffects.BUTTON_CLICK.soundEvent).enableDynamicBackground().setDisplayText(ClientReference.localize("oxygen.gui.confirmButton"), true, GUISettings.instance().getButtonTextScale()));
        this.addElement(this.cancelButton = new GUIButton(this.getWidth() - 55, this.getHeight() - 12, 40, 10).setSound(OxygenSoundEffects.BUTTON_CLICK.soundEvent).enableDynamicBackground().setDisplayText(ClientReference.localize("oxygen.gui.cancelButton"), true, GUISettings.instance().getButtonTextScale()));
    }

    @Override
    public void handleElementClick(AbstractGUISection section, GUIBaseElement element, int mouseButton) {
        if (mouseButton == 0) {
            if (element == this.cancelButton)
                this.close();
            else if (element == this.confirmButton) {
                GroupsManagerClient.instance().startReadinessCheckSynced();
                this.close();            
                this.screen.close();
            }
        }
    }
}
