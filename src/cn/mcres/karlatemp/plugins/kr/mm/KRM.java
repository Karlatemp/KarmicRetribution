package cn.mcres.karlatemp.plugins.kr.mm;

import cn.mcres.karlatemp.plugins.kr.INT;
import cn.mcres.karlatemp.plugins.kr.KrAPI;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import io.lumine.xikage.mythicmobs.skills.mechanics.DamageMechanic;
import java.util.UUID;

public class KRM extends DamageMechanic {

    public KRM(String line, MythicLineConfig mlc) {
        super(line, mlc);
    }

    @Override
    public boolean castAtEntity(SkillMetadata data, AbstractEntity target) {
        if (target.isPlayer()) {
            UUID id = target.asPlayer().getUniqueId();
            INT karma = KrAPI.getKarma(id);
            karma.value += (int) this.amount;
            return true;
        } else {
            return super.castAtEntity(data, target);
        }
    }

}
