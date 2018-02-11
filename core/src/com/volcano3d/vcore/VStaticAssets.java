package com.volcano3d.vcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class VStaticAssets {
	//Called in VMain constructor before create()
	public static void Init(){
		Fonts.calibri25Font.getData().setLineHeight(25);
		GUI.buttonsSkin.addRegions(new TextureAtlas("gui.txt"));
	}
	public static class Text{
		public static final String magmaticProcessText = "Minerāli rodas tieši no magmas, tai atdziestot, sacietējot un izveidojot kristāla graudus. Magmatiskie minerālu veidošanās procesi notiek ļoti augstas temperatūras un parasti - liela spiediena apstākļos.";
		public static final String pegmatiteProcessText = "Minerāli rodas, kristalizējoties tā sauktajam magmatiskajam atlikumam, kas paliek pāri pēc tam, kad galvenā magma jau ir pārvērtusies minerālu graudos. Raksturīgi daudz lielāki minerālu graudu izmēri.";
		public static final String hydrothermalProcessText = "Minerāli izdalās no karstiem ūdens šķīdumiem, kas savukārt rodas iežu plaisās, kondensējoties magmatiskas izcelsmes ūdens tvaikiem. Šiem šķīdumiem pārvēršoties minerālos, veidojas visdažādāko metāla rūdu un tīrradņu dzīslu atradnes.";
		public static final String pneymatholiticProcessText = "Minerāli veidojas no gāzēm, kas izdalās magmatiskajos vai pegmatītu veidošanās procesos. Šie procesi notiek Zemes virsū vulkānisko gāzu izplūdes vietās vai arī iežu plaisās.";
		public static final String methamorphicProcessText = "Minerāli rodas, kad zemes dzīlēs jau esoši (agrāk izveidojušies) minerāli izmainās paaugstināta spiediena un temperatūras dēļ. Tas notiek, kontaktējoties ar magmu vai reaģējot ar ķīmiski aktīviem šķīdumiem, tvaikiem un gāzēm tādā dziļumā, kurā vairs nesniedzas minerālu veidošanās procesi, kas notiek Zemes virspusē.";
	}
	public static class ActorActionsPaths{
		public static final float[][] pathView1ButtonMoveIn = {
		        {0.489796f, 0.012500f},
		        {0.497959f, 0.226250f},
		        {0.322449f, 0.215000f},
		        {0.053061f, 0.261250f},
		        {0.038775f, 0.427500f},
		        {0.116326f, 0.700000f},
		        {0.606122f, 0.758750f},
		        {0.914286f, 0.506250f},
		        {0.781633f, 0.350000f}
			};
	}
	public static class Fonts{
		public static final BitmapFont calibriLightFont = new BitmapFont(Gdx.files.internal("fonts/calibri-light-50.fnt"));
		public static final BitmapFont calibri25Font = new BitmapFont(Gdx.files.internal("fonts/calibri-25.fnt"));
		public static final BitmapFont calibri18Font = new BitmapFont(Gdx.files.internal("fonts/calibri-18.fnt"));		
	}
	public static class GUI{
		public static final Skin buttonsSkin = new Skin();
	}
}
