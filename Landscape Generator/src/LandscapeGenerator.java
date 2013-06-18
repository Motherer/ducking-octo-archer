
public class LandscapeGenerator {
	public LandscapeGenerator(){
		
	}
	public Tile[][] generate(int x, int y){
		Tile[][] landscape = new Tile[x][y];
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				
				landscape[i][j] = randomTile(landscape,i,j);
//				switch (type){
//				case 0:
//					landscape[i][j]=new Tile(TypeEnum.FORREST);
//					break;
//				case 1:
//					landscape[i][j]=new Tile(TypeEnum.PLAIN);
//					break;
//				case 2:
//					landscape[i][j]=new Tile(TypeEnum.MOUNTAIN);
//					break;
//				case 3:
//					landscape[i][j]=new Tile(TypeEnum.WATER);
//					break;
//				}
			}
		}
		return landscape;
	}
	private Tile randomTile(Tile[][] landscape, int i, int j) {
		Tile tile = new Tile();
		if(i>1){
			tile.setWestTile(landscape[i-1][j]);
		}
		if(j>1){
			tile.setNorthTile(landscape[i][j-1]);
		}
		tile.randomizeType();
		return tile;
	}
}