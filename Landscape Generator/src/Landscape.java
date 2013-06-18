public class Landscape {
	private Tile[][] landscape;
	private int sizeX;
	private int sizeY;

	public Landscape(Tile[][] landscape) {
		this.landscape = landscape;
		sizeX = landscape.length;
		sizeY = landscape[0].length;
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
				System.out.println("Zombie moved!!!");
				return true;
			}
		}
		return false;
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
}