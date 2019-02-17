package bertrand.myopengl.Tool.OBJ_FILE;

import bertrand.myopengl.Tool.Vec3;

public class VertexInfo {
	private static final int NO_INDEX = -1;
	
	private Vec3 v;
        private VertexInfo next = null;
        private int selfIdx;
	private int textureIdx = NO_INDEX;
	private int normalIdx = NO_INDEX;
	
	public VertexInfo(int index, Vec3 position){
		this.selfIdx = index;
		this.v = position;
	}
	
	public int idx(){
		return selfIdx;
	}

	
	public boolean missTextureIdx(){
		return textureIdx ==NO_INDEX || normalIdx ==NO_INDEX;
	}
	
	public boolean hasEqual(int textureIndexOther, int normalIndexOther){
		return textureIndexOther== textureIdx && normalIndexOther== normalIdx;
	}
	
	public void setIndex(int textureIndex, int normalIndex){
		this.textureIdx = textureIndex;
		this.normalIdx = normalIndex;
	}

	public Vec3 v() {
		return v;
	}

	public int getTextureIndex() {
		return textureIdx;
	}

	public int getNormalIndex() {
		return normalIdx;
	}

	public VertexInfo next() {
		return next;
	}

	public void next(VertexInfo next) {
		this.next = next;
	}

}
