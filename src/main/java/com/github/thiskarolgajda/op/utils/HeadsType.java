package com.github.thiskarolgajda.op.utils;

import lombok.Getter;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.tools.Heads;

@Getter
public enum HeadsType {
    GREY_HEAD("7af6fab767ca4d7df6217b895b667bcacc524d407068619f819a070f3f629ce0"),
    INFORMATION("6d20f15a5a7eca32ff193c10f5d1d3872858c92e2ba0acbf6028523c9772e5ae"),
    VILLAGE("686e588c12ce23d27482c6d5c1a331a729bce404254610b00f1951a45a733ae6"),
    OWNER_VILLAGE("c5a35b5ca15268685c4660535e5883d21a5ec57c55d397234269acb5dc2954f"),
    ACHIEVEMENTS("b7b49b7dd1b911bf679b9969caed91824d34a68cc1541791fadd1d4654dd01dd"),
    NAME_TAG("2ad8a3a3b36add5d9541a8ec970996fbdcdea9414cd754c50e48e5d34f1bf60a"),
    EXPERIENCE_CUBE("399ad7a0431692994b6c412c7eafb9e0fc49975240b73a27d24ed797035fb894"),
    TREASURE_CHEST_GOLD("c3bdbaedd7d6444e79aa8222f8981240204cc3cc1c9655118687618db8cec"),
    SPECIALITY("a049904a31117e8078aa1243f0623b47d06ea9ac5ea299031c35dfa89ddd8ee8"),
    HOUSE("ff0dbee370877feb4a62ac9f93e13fa5de04d313ef83feee815f6ed3a7abca5c"),
    GOLD_BLOCK_SWORD("d6cc6b83763a67fcada1ea184c2d1752ad240746c6be258a73983d8b657f4bb5"),
    MARKET_STAND("533fc9a45be13ca57a78b21762c6e1262dae411f13048b963d972a29e07096ab"),
    KING("c6e57726de60b1612857280116b2de609613ff342e93f10662391ea65bb3f939"),
    MONEY_BAG("9fd108383dfa5b02e86635609541520e4e158952d68c1c8f8f200ec7e88642d"),
    WAX_SEAL_RED_SWORD("41cab467c66944b7d5214105961f848928aead67940d810d7d797060237918d7"),
    WAX_SEAL_RED_SKULL("3e881eb8165f2610609a3ae0c4d93a2e1810ad4fe2d8477096802f7a2a0de47c"),
    WAX_SEAL_RED_SHIELD("dde1dd70e54f453aaff11de7e51d2fb49e2641e491a69b6ac1e9f6a4c6bcd39a"),
    WAX_SEAL_RED_MUSHROOM("56d52482eda57aabdd3a594e5bd4b3533e6e04d492e8c8aa587e93543a7a1051"),
    WAX_SEAL_RED_MOON("dc46fc852a2b925dd86823f60499b7c14372c1b7d7b6947d6cd0d6e045d5c84b"),
    WAX_SEAL_RED_EYE("9e092b7ca898e8ef960ede1d0fbc81bb744c6fc96623d43419693b4b49f6581"),
    WAX_SEAL_RED_CROWN("4ea385dc612a301fbe8fa3094771b45c488d960fecee0af845d4376365702d4d"),
    WAX_SEAL_RED_CROSS("4ce4f75e2c1f19c66ec031ee2fff5cfb230db8354290f48d7c81ad88b16c759a"),
    WAX_SEAL_RED_CREEPER("413484424f1866e076a40feb84f3f76e2fc9ab22c701c341a4c893454b2c14f2"),
    WAX_SEAL_RED_CIRCLE("703cd0301b39022a90eeeab72bd3cf8e2ba8a4db4dba2ea71027d84bce7e4d17"),
    WAX_SEAL_RED_CHECKMARK("cc6746eeb456f62d397e292cbdf29f23a735b0c99b27db4ef05fd1242f7760e1"),
    WAX_SEAL_RED_BLANK("93a7f16a76ce01c344e5be0b0e9fc0bde565e76c8d266a68ced2e1a952d7806e"),
    TROPHY("e34a592a79397a8df3997c43091694fc2fb76c883a76cce89f0227e5c9f1dfe"),
    WARP("481ba8a0d52d2d79c86d5f7d9ccc7b1a80648bb7de79107971f48af13be54e12"),
    HOME("12d7a751eb071e08dbbc95bc5d9d66e5f51dc6712640ad2dfa03defbb68a7f3a"),
    COLOR_BOX("f4e94273c727b1f2c9376b5cae4ed9a48d5851bd2ab2fd83d5f81a6e6aff193d"),
    SMILING_STEVE("8a234c2def829ba73ee1c048403e5f91316f0b5ce2bf7e70a4ce31fbe90d2ed3"),
    FLYING_PIG("90e1ed3f83e3c949824adf57b976d4f8cabbb90d3b07d90cac7f68f01eb8751e");

    private final String texture;

    HeadsType(String texture) {
        this.texture = texture;
    }

    public ItemBuilder getHead() {
        return Heads.get(getTexture());
    }
}
