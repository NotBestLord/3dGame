package com.notbestlord.core.particle;

import com.notbestlord.core.utils.Consts;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Particle {
	private Vector3f position;
	private Vector3f velocity;
	private float gravityEffect;
	private float lifeLength;
	private float rotation;
	private float scale;
	private ParticleTexture texture;
	private Vector2f texOffset1 = new Vector2f();
	private Vector2f texOffset2 = new Vector2f();
	private float blend;

	private float elapsedTime = 0;

	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation, float scale) {
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		this.texture = texture;
	}

	public boolean update(){
		velocity.y -= Consts.GRAVITY_ACCEL * gravityEffect * Consts.deltaTime;
		position.add(new Vector3f(velocity).mul(Consts.deltaTime));
		updateTextureCoordInfo();
		elapsedTime += Consts.deltaTime;
		return elapsedTime < lifeLength;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}

	public ParticleTexture getTexture() {
		return texture;
	}

	public Vector2f getTexOffset1() {
		return texOffset1;
	}

	public Vector2f getTexOffset2() {
		return texOffset2;
	}

	public float getBlend() {
		return blend;
	}

	private void updateTextureCoordInfo() {
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		setTextureOffset(texOffset1, index1);
		setTextureOffset(texOffset2, index2);
	}

	private void setTextureOffset(Vector2f offset,  int index){
		int column = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();
		offset.x = column / (float) texture.getNumberOfRows();
		offset.y = row / (float) texture.getNumberOfRows();
	}
}
