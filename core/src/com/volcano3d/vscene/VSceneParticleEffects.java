package com.volcano3d.vscene;

import com.volcano3d.vcamera.VCamera;
import com.volcano3d.vcore.VMain;
import com.volcano3d.vparticles.VParticleEffect;

public class VSceneParticleEffects {

	protected VMain volcano;	
	
    public VParticleEffect	particleFireSmoke = null;
    public VParticleEffect	particleSmokeCloud = null;
    public VParticleEffect	particleSmokeCloudIsland = null;
    public VParticleEffect	particleSecondaryFire = null;
    public VParticleEffect	particleSmoke1 = null;  
    public VParticleEffect	particleSmoke2 = null;  
    public VParticleEffect	particleSmoke3 = null;  	
	
    public VSceneParticleEffects(VMain o){
    	volcano = o;
    }	

    public void create(){
        particleFireSmoke = new VParticleEffect(volcano, "volcanoFire2.pfx");
        particleFireSmoke.setPosition(-214, 115, -10);
        
        particleSmokeCloud = new VParticleEffect(volcano, "smokeCloud.pfx");
        particleSmokeCloud.setPosition(-220, 115, -10);        
        
        particleSmokeCloudIsland = new VParticleEffect(volcano, "smokeIsland.pfx");        
        particleSmokeCloudIsland.setPosition(1400, 55, 20);
        
        particleSecondaryFire = new VParticleEffect(volcano, "fireSecondary.pfx");
        particleSecondaryFire.setPosition(-270, 65, -30);
                
        particleSmoke1 = new VParticleEffect(volcano, "smoke1.pfx");
        particleSmoke1.setPosition(-180, 40, -40);
        
        particleSmoke2 = new VParticleEffect(volcano, "smoke2.pfx");
        particleSmoke2.setPosition(-270, 40, -50);  
        
        particleSmoke3 = new VParticleEffect(volcano, "smoke3.pfx");
        particleSmoke3.setPosition(-200, 20, -90);
    }
    
    public void onLoad(){
        particleFireSmoke.onLoad();
        particleSmokeCloudIsland.onLoad();
        particleSmokeCloud.onLoad();
        particleSecondaryFire.onLoad();
        particleSmoke1.onLoad();
        particleSmoke2.onLoad();
        particleSmoke3.onLoad();
    }
    
    public void render(VCamera camera){
        particleSmokeCloudIsland.render(camera.get());        
        particleSmoke1.render(camera.get()); 
        particleSmoke2.render(camera.get()); 
        particleSmoke3.render(camera.get()); 
        particleSmokeCloud.render(camera.get());        
        particleFireSmoke.render(camera.get());  
        particleSecondaryFire.render(camera.get());    	
    }
}
