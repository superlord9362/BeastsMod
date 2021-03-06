function initializeCoreMod() {
    return {
        'coremodmethod': {
            'target': {
                'type': 'METHOD',
                'class': 'net.minecraft.world.chunk.IChunk',
                'methodName': 'getBiome',
                'methodDesc': '(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/world/biome/Biome;'
            },
            'transformer': function (method) {
                var Opcodes = Java.type('org.objectweb.asm.Opcodes');
                var FrameNode = Java.type('org.objectweb.asm.tree.FrameNode');
                var InsnNode = Java.type('org.objectweb.asm.tree.InsnNode');
                var VarInsnNode = Java.type('org.objectweb.asm.tree.VarInsnNode');
                var JumpInsnNode = Java.type('org.objectweb.asm.tree.JumpInsnNode');
                var MethodInsnNode = Java.type('org.objectweb.asm.tree.MethodInsnNode');
                var instructions = method.instructions;
                var label = instructions.getFirst();
                instructions.insert(label, new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
                instructions.insertBefore(label, new InsnNode(Opcodes.ARETURN));
                instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 3));
                instructions.insertBefore(instructions.getFirst(), new JumpInsnNode(Opcodes.IFNULL, label));
                instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 3));
                instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ASTORE, 3));
                instructions.insertBefore(instructions.getFirst(), new MethodInsnNode(Opcodes.INVOKESTATIC, "random/beasts/api/world/biome/underground/UndergroundBiome", "getBiome", "(Lnet/minecraft/util/math/BlockPos;Ljava/lang/Object;)Lnet/minecraft/world/biome/Biome;", false));
                instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 0));
                instructions.insertBefore(instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 1));
                return method;
            }
        }
    };
}
