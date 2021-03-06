import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Landscape implements MouseListener {
	private Tile[][] landscape;
	private int sizeX;
	private int sizeY;
	private ImageViewer imageViewer;

	public Landscape(Tile[][] landscape, ImageViewer imageViewer) {
		this.landscape = landscape;
		sizeX = landscape.length;
		sizeY = landscape[0].length;
		this.imageViewer = imageViewer;
	}

	public Tile getTile(int x, int y) {
		if (x >= 0 && x < sizeX && y >= 0 && y < sizeY) {
			return landscape[x][y];
		}
		return null;
	}

	public synchronized boolean moveZombie(int oldX, int oldY, int newX,
			int newY) {
		if (validateMove(oldX, oldY, newX, newY) && !isWater(newX, newY)) {
			if (!landscape[newX][newY].isInfested()) {
				landscape[newX][newY]
						.infest(landscape[oldX][oldY].isInfested());
				landscape[oldX][oldY].infest(false);
				if (landscape[newX][newY].isInhabited()) {
					landscape[newX][newY].inhabited(false);
					System.out.println("The people at tile x: " + newX + " y: "
							+ newY + " was turned into zombies!!!");
					spawnZombie(newX, newY);
				}
				return true;
			}
		}
		return false;
	}

	private void spawnZombie(int x, int y) {
		if (validateMove(0, 0, x, y) && !isWater(x, y)) {
			if (!landscape[x][y].isInfested()) {
				landscape[x][y].infest(true);
				new ZombieThread(x, y, this, imageViewer).start();
			} else {
				if (!landscape[x - 1][y].isInfested()
						&& validateMove(0, 0, x - 1, y) && !isWater(x - 1, y)) {
					landscape[x - 1][y].infest(true);
					new ZombieThread(x - 1, y, this, imageViewer).start();
				} else if (!landscape[x - 1][y - 1].isInfested()
						&& validateMove(0, 0, x - 1, y - 1)
						&& !isWater(x - 1, y - 1)) {
					landscape[x - 1][y - 1].infest(true);
					new ZombieThread(x - 1, y - 1, this, imageViewer).start();
				} else if (!landscape[x - 1][y + 1].isInfested()
						&& validateMove(0, 0, x - 1, y + 1)
						&& !isWater(x - 1, y + 1)) {
					landscape[x - 1][y + 1].infest(true);
					new ZombieThread(x - 1, y + 1, this, imageViewer).start();
				} else if (!landscape[x + 1][y].isInfested()
						&& validateMove(0, 0, x + 1, y) && !isWater(x + 1, y)) {
					landscape[x + 1][y].infest(true);
					new ZombieThread(x + 1, y, this, imageViewer).start();
				} else if (!landscape[x + 1][y + 1].isInfested()
						&& validateMove(0, 0, x + 1, y + 1)
						&& !isWater(x + 1, y + 1)) {
					landscape[x + 1][y + 1].infest(true);
					new ZombieThread(x + 1, y + 1, this, imageViewer).start();
				} else if (!landscape[x + 1][y - 1].isInfested()
						&& validateMove(0, 0, x + 1, y - 1)
						&& !isWater(x + 1, y - 1)) {
					landscape[x + 1][y - 1].infest(true);
					new ZombieThread(x + 1, y - 1, this, imageViewer).start();
				} else if (!landscape[x][y - 1].isInfested()
						&& validateMove(0, 0, x, y - 1) && !isWater(x, y - 1)) {
					landscape[x][y - 1].infest(true);
					new ZombieThread(x, y - 1, this, imageViewer).start();
				} else if (!landscape[x][y + 1].isInfested()
						&& validateMove(0, 0, x, y + 1) && !isWater(x, y + 1)) {
					landscape[x][y + 1].infest(true);
					new ZombieThread(x, y + 1, this, imageViewer).start();
				}
			}
		}
	}

	private boolean isWater(int x, int y) {
		if (x < 0 || x >= sizeX) {
			return false;
		}
		if (y < 0 || y >= sizeY) {
			return false;
		}

		return landscape[x][y].getType() == TypeEnum.WATER;
	}

	private boolean validateMove(int oldX, int oldY, int newX, int newY) {
		if (oldX < 0 || oldX >= sizeX) {
			return false;
		}
		if (oldY < 0 || oldY >= sizeY) {
			return false;
		}
		if (newX < 0 || newX >= sizeX) {
			return false;
		}
		if (newY < 0 || newY >= sizeY) {
			return false;
		}
		return true;
	}

	public BufferedImage getLandscapeImg(int pixelX, int pixelY) {
		int type = BufferedImage.TYPE_INT_ARGB;
		BufferedImage image = new BufferedImage(pixelX * sizeX, pixelY * sizeY,
				type);
		for (int j = 0; j < sizeX; j++) {
			for (int i = 0; i < sizeY; i++) {
				image.createGraphics().drawImage(
						getTile(i, j).getTileImage(pixelX, pixelY), i * pixelX,
						j * pixelY, null);
			}
		}
		return image;
	}

	public void mouseClicked(MouseEvent e) {
		System.out.println("X:" + e.getX());
		System.out.println("Y:" + e.getY());

		System.out.println("Tile X:" + (e.getX() / 10));
		System.out.println("Tile Y:" + (e.getY() / 10));
		spawnHuman(e.getX()/10,e.getY()/10);
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void spawnHuman(int x, int y) {
		if (validateMove(0, 0, x, y) && !isWater(x, y)) {
			if (!landscape[x][y].isInfested()
					&& validateMove(0, 0, x, y) && !isWater(x, y)
					&& !landscape[x][y].isInhabited()) {
				landscape[x][y].inhabited(true);
				new HumanThread(x, y, this, imageViewer).start();
			}else if (!landscape[x - 1][y].isInfested()
					&& validateMove(0, 0, x - 1, y) && !isWater(x - 1, y)
					&& !landscape[x - 1][y].isInhabited()) {
				landscape[x - 1][y].inhabited(true);
				new HumanThread(x - 1, y, this, imageViewer).start();
			} else if (!landscape[x - 1][y - 1].isInfested()
					&& validateMove(0, 0, x - 1, y - 1)
					&& !isWater(x - 1, y - 1)
					&& !landscape[x - 1][y - 1].isInhabited()) {
				landscape[x - 1][y - 1].inhabited(true);
				new HumanThread(x - 1, y - 1, this, imageViewer).start();
			} else if (!landscape[x - 1][y + 1].isInfested()
					&& validateMove(0, 0, x - 1, y + 1)
					&& !isWater(x - 1, y + 1)
					&& !landscape[x - 1][y + 1].isInhabited()) {
				landscape[x - 1][y + 1].inhabited(true);
				new HumanThread(x - 1, y + 1, this, imageViewer).start();
			} else if (!landscape[x + 1][y].isInfested()
					&& validateMove(0, 0, x + 1, y) && !isWater(x + 1, y)
					&& !landscape[x + 1][y].isInhabited()) {
				landscape[x + 1][y].inhabited(true);
				new HumanThread(x + 1, y, this, imageViewer).start();
			} else if (!landscape[x + 1][y + 1].isInfested()
					&& validateMove(0, 0, x + 1, y + 1)
					&& !isWater(x + 1, y + 1)
					&& !landscape[x + 1][y + 1].isInhabited()) {
				landscape[x + 1][y + 1].inhabited(true);
				new HumanThread(x + 1, y + 1, this, imageViewer).start();
			} else if (!landscape[x + 1][y - 1].isInfested()
					&& validateMove(0, 0, x + 1, y - 1)
					&& !isWater(x + 1, y - 1)
					&& !landscape[x + 1][y - 1].isInhabited()) {
				landscape[x + 1][y - 1].inhabited(true);
				new HumanThread(x + 1, y - 1, this, imageViewer).start();
			} else if (!landscape[x][y - 1].isInfested()
					&& validateMove(0, 0, x, y - 1) && !isWater(x, y - 1)
					&& !landscape[x][y - 1].isInhabited()) {
				landscape[x][y - 1].inhabited(true);
				new HumanThread(x, y - 1, this, imageViewer).start();
			} else if (!landscape[x][y + 1].isInfested()
					&& validateMove(0, 0, x, y + 1) && !isWater(x, y + 1)
					&& !landscape[x][y + 1].isInhabited()) {
				landscape[x][y + 1].inhabited(true);
				new HumanThread(x, y + 1, this, imageViewer).start();
			}
		}
	}
}
