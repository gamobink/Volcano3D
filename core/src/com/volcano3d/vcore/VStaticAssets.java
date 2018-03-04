package com.volcano3d.vcore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class VStaticAssets {
	//Called in VMain constructor before create()
	public static void Init(){
		Fonts.calibri25Font.getData().setLineHeight(25);
		Fonts.calibriLightFont.getData().setLineHeight(45);
		Fonts.futuraFont.getData().setLineHeight(25);
		//TODO Button on/off state images
		GUI.buttonsSkin.addRegions(new TextureAtlas("gui.txt"));
	}
	public static class Text{

		public static final String magmaticProcessTitle = "Magmatiskie procesi";
		public static final String pegmatiteProcessTitle = "Pegmatītu veidošanās procesi";
		public static final String hydrothermalProcessTitle = "Hidrotermālie procesi";
		public static final String pneymatholiticProcessTitle = "Pneimatolītiskie procesi";
		public static final String methamorphicProcessTitle = "Metamorfisma procesi";
		public static final String chemicalProcessTitle = "Dēdēšanas procesi";
		public static final String organicProcessTitle = "Bioloģiskie (organiskie) procesi";
		public static final String sedimentationProcessTitle = "Sedimentācijas (nogulsnēšanās) procesi";
		
		public static final String magmaticProcessText = "Minerāli rodas tieši no magmas, tai atdziestot, sacietējot un izveidojot kristāla graudus. Magmatiskie minerālu veidošanās procesi notiek ļoti augstas temperatūras un parasti - liela spiediena apstākļos.";
		public static final String pegmatiteProcessText = "Minerāli rodas, kristalizējoties tā sauktajam magmatiskajam atlikumam, kas paliek pāri pēc tam, kad galvenā magma jau ir pārvērtusies minerālu graudos. Raksturīgi daudz lielāki minerālu graudu izmēri.";
		public static final String hydrothermalProcessText = "Minerāli izdalās no karstiem ūdens šķīdumiem, kas savukārt rodas iežu plaisās, kondensējoties magmatiskas izcelsmes ūdens tvaikiem. Šiem šķīdumiem pārvēršoties minerālos, veidojas visdažādāko metāla rūdu un tīrradņu dzīslu atradnes.";
		public static final String pneymatholiticProcessText = "Minerāli veidojas no gāzēm, kas izdalās magmatiskajos vai pegmatītu veidošanās procesos. Šie procesi notiek Zemes virsū vulkānisko gāzu izplūdes vietās vai arī iežu plaisās.";
		public static final String methamorphicProcessText = "Minerāli rodas, kad zemes dzīlēs jau esoši (agrāk izveidojušies) minerāli izmainās paaugstināta spiediena un temperatūras dēļ. Tas notiek, kontaktējoties ar magmu vai reaģējot ar ķīmiski aktīviem šķīdumiem, tvaikiem un gāzēm tādā dziļumā, kurā vairs nesniedzas minerālu veidošanās procesi, kas notiek Zemes virspusē.";
		public static final String chemicalProcessText = "Jau agrāk radušies minerāli pārveidojas – izmainās atmosfēras gāzu, ūdens un organismu ķīmiskās iedarbības rezultātā. Jaunie (dēdēšanas) minerāli rodas galvenokārt no endogēno (Zemes iekšienē) minerālu noārdīšanas materiāla.";
		public static final String organicProcessText = "Minerāli visbiežāk rodas ezeros un jūrās, kur uzkrājas dzīvo organismu, galvenokārt mikroorganismu un planktona, minerālie skeleti un čaulas. Tādā veidā radies biogēnais kalcīts.";
		public static final String sedimentationProcessText = "Notiek ūdens vidē, minerāliem izgulsnējoties no pārsātinātiem sāļu šķīdumiem. Kristāliskie minerālu graudi parasti veidojas izžūstošos ezeros vai izolētos lagūnu tipa baseinos sausā un karstā klimatā.";
		
		public static final String titleText = "Minerālu veidošanās procesi";
		public static final String intro1Text = "Gandrīz visi minerāli uz Zemes ir radušies dabiskos ģeoloģiskos procesos.";
		public static final String intro2Text = "Iežu aprites ciklā jaunu minerālu veidošanās – to pārveidošanās, noārdīšanās un citu minerālu veidošanās – ir ģeoloģisko procesu secīga virkne.";		
		public static final String intro3Text = "Lielākā daļa minerālu ir radušies no šķidrām vielām – dažāda sastāva kausējumiem un maisījumiem, kuri sastāv no sīki sasmalcinātām vielām. Pārējo minerālu izcelšanās ir saistīta ar gāzveida vielas kristalizāciju un ķīmiskajiem aizvietošanas un pārkristalizēšanās procesiem cietās vielās.";				
		public static final String intro4Text = "Visus minerālu veidošanās procesus iedala 2 grupās: endogēnos un eksogēnos procesos.";				
		public static final String intro5Text = "Endogēnie procesi gandrīz vienmēr notiek Zemes iekšienē lielākā vai mazākā dziļumā paaugstināta spiediena un augstas temperatūras apstākļos. Endogēnos procesus iedala vēl sīkāk: magmatiskie, pegmatītu, pneimatolītiskie, hidrotermālie un metamorfisma.";
		public static final String intro6Text = "Eksogēnie minerālu veidošanās procesi notiek Zemes virspusē vai ļoti nelielā dziļumā zemas temperatūras un tāda spiediena apstākļos, kas ir tuvs atmosfēras spiedienam. Eksogēnos procesus vēl sīkāk iedala dēdēšanas, sedimentācijas un bioloģiskajos procesos.";
		
		public static final String chem1 = "Malahīts";
		public static final String chem2 = "Kaolinīts";
		public static final String chem3 = "Eritrins";
		public static final String hidro1 = "Zelts";
		public static final String hidro2 = "Galenīts";
		public static final String hidro3 = "Realgars";
		public static final String hidro4 = "";
		public static final String magm1 = "Kvarcs";
		public static final String magm2 = "Sodalīts";
		public static final String magm3 = "Apatīts";
		public static final String magm4 = "";
		public static final String metam1 = "Kianīts";
		public static final String metam2 = "Talks";
		public static final String metam3 = "Tremolīts";
		public static final String metam4 = "";
		public static final String metam5 = "";
		public static final String organ1 = "Organogenais kaļķakmens";
		public static final String organ2 = "";
		public static final String pegma1 = "Spodumens";
		public static final String pegma2 = "Mikroklīns";
		public static final String pegma3 = "Berils";
		public static final String pneim1 = "Sērs";
		public static final String pneim2 = "Opāls";
		public static final String pneim3 = "Stilbits";
		public static final String pneim4 = "";
		public static final String pneim5 = "";
		public static final String sedim1 = "Halīts";
		public static final String sedim2 = "Silvīns";
		public static final String sedim3 = "Ģipsis";
		public static final String sedim4 = "";
	}
	public static class ActorActionsPaths{
		public static final float[][] pathView1ButtonMoveIn = {
				{0.424490f, 0.012500f},
				{0.465306f, 0.197500f},
				{0.251020f, 0.261250f},
				{0.004082f, 0.221250f},
				{-0.022449f, 0.428750f},
				{-0.146939f, 0.710000f},
				{0.736735f, 0.791250f},
				{1.006122f, 0.525000f},
				{0.855102f, 0.215000f},
				{0.508163f, 0.133750f},
				{0.214286f, 0.263750f},
			};
		public static final float[][] pathView2ButtonMoveIn = {
				{0.424490f, 0.012500f},
				{0.465306f, 0.197500f},
				{0.251020f, 0.261250f},
				{0.024490f, 0.231250f},
				{-0.022449f, 0.428750f},
				{-0.146939f, 0.710000f},
				{0.789796f, 0.702500f},
				{0.932653f, 0.513750f},
				{0.765306f, 0.203750f},
				{0.410204f, 0.213750f},
		};
		public static final float[][] pathView3ButtonMoveIn = {
				{0.424490f, 0.012500f},
				{0.465306f, 0.197500f},
				{0.251020f, 0.261250f},
				{0.106122f, 0.177500f},
				{-0.022449f, 0.428750f},
				{-0.146939f, 0.710000f},
				{0.814286f, 0.691250f},
				{0.855102f, 0.411250f},
				{0.610204f, 0.250000f},
		};
		public static final float[][] pathView4ButtonMoveIn = {
				{0.424490f, 0.012500f},
				{0.461224f, 0.275000f},
				{0.077551f, 0.183750f},
				{-0.057143f, 0.385000f},
				{0.020408f, 0.678750f},
				{0.730612f, 0.631250f},
				{0.700000f, 0.376250f},
		};		
		public static final float[][] pathView5ButtonMoveIn = {
				{0.424490f, 0.012500f},
				{0.481633f, 0.282500f},
				{-0.038776f, 0.196250f},
				{-0.108163f, 0.501250f},
				{0.422449f, 0.648750f},
				{0.606122f, 0.502500f},
		};		
		public static final float[][] pathView6ButtonMoveIn = {
				{0.424490f, 0.012500f},
				{0.475510f, 0.266250f},
				{0.034694f, 0.182500f},
				{0.008163f, 0.446250f},
				{0.187755f, 0.543750f},
				{0.410204f, 0.543750f},
		};		
		public static final float[][] pathView7ButtonMoveIn = {
				{0.424490f, 0.012500f},
				{0.471429f, 0.228750f},
				{0.138775f, 0.205000f},
				{0.038775f, 0.373750f},
				{0.204082f, 0.500000f},
		};		
		public static final float[][] pathView8ButtonMoveIn = {
				{0.424490f, 0.012500f},
				{0.461224f, 0.211250f},
				{0.163265f, 0.205000f},
				{0.132653f, 0.373750f},
		};		
		
		public static final float[][] pathView3ButtonMoveOut = {
				{0.424490f, 0.012500f},
				{0.383674f, 0.197500f},
				{0.597960f, 0.261250f},
				{0.844898f, 0.221250f},
				{0.871429f, 0.428750f},
				{0.995919f, 0.710000f},
				{0.112245f, 0.791250f},
				{-0.157142f, 0.525000f},
				{-0.006122f, 0.215000f},
				{0.340817f, 0.133750f},
				{0.634694f, 0.263750f},
		};
		public static final float[][] pathView2ButtonMoveOut = {
				{0.424490f, 0.012500f},
				{0.383674f, 0.197500f},
				{0.597960f, 0.261250f},
				{0.824490f, 0.231250f},
				{0.871429f, 0.428750f},
				{0.995919f, 0.710000f},
				{0.059184f, 0.702500f},
				{-0.083673f, 0.513750f},
				{0.083674f, 0.203750f},
				{0.438776f, 0.213750f},	
		};
		public static final float[][] pathView1ButtonMoveOut = {
				{0.424490f, 0.012500f},
				{0.383674f, 0.197500f},
				{0.597960f, 0.261250f},
				{0.742858f, 0.177500f},
				{0.871429f, 0.428750f},
				{0.995919f, 0.710000f},
				{0.034694f, 0.691250f},
				{-0.006122f, 0.411250f},
				{0.238776f, 0.250000f},
		};
		public static final float[][] pathView8ButtonMoveOut = {
				{0.424490f, 0.012500f},
				{0.387756f, 0.275000f},
				{0.771429f, 0.183750f},
				{0.906123f, 0.385000f},
				{0.828572f, 0.678750f},
				{0.118368f, 0.631250f},
				{0.148980f, 0.376250f},	
		};	
		public static final float[][] pathView7ButtonMoveOut = {
				{0.424490f, 0.012500f},
				{0.367347f, 0.282500f},
				{0.887756f, 0.196250f},
				{0.957143f, 0.501250f},
				{0.426531f, 0.648750f},
				{0.242858f, 0.502500f},
		};
		public static final float[][] pathView6ButtonMoveOut = {
				{0.424490f, 0.012500f},
				{0.373470f, 0.266250f},
				{0.814286f, 0.182500f},
				{0.840817f, 0.446250f},
				{0.661225f, 0.543750f},
				{0.438776f, 0.543750f},	
		};
		public static final float[][] pathView5ButtonMoveOut = {
				{0.424490f, 0.012500f},
				{0.377551f, 0.228750f},
				{0.710205f, 0.205000f},
				{0.810205f, 0.373750f},
				{0.644898f, 0.500000f},	
		};
		public static final float[][] pathView4ButtonMoveOut = {
				{0.424490f, 0.012500f},
				{0.387756f, 0.211250f},
				{0.685715f, 0.205000f},
				{0.716327f, 0.373750f},
		};			
	}
	public static class Fonts{
		public static final BitmapFont calibriLightFont = new BitmapFont(Gdx.files.internal("fonts/calibri-light-50.fnt"));
		public static final BitmapFont calibri25Font = new BitmapFont(Gdx.files.internal("fonts/calibri-25.fnt"));
		public static final BitmapFont calibri18Font = new BitmapFont(Gdx.files.internal("fonts/calibri-18.fnt"));
		public static final BitmapFont futuraFont = new BitmapFont(Gdx.files.internal("fonts/futura-32.fnt"));
	}
	public static class GUI{
		public static final Skin buttonsSkin = new Skin();
	}
}
