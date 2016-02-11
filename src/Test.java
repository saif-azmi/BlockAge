/**
 * Created by Dominic on 05/02/2016.
 */
public class Test
{
	public static void test(GameRunTime runTime)
	{
		SpriteImage spriteBlockade = new SpriteImage("http://imgur.com/dZZdmUr.png", null);
		Blockade block = new Blockade(0, "block", "desc", new GraphNode(2, 2), spriteBlockade);
		spriteBlockade.setOnMouseClicked(e ->
		{
			runTime.getRenderer().produceRouteVisual(runTime.getEngine().getGraph().getNodes());
		});
		spriteBlockade.setEntity(block);
		//unit//
		SpriteImage sprite = new SpriteImage("http://imgur.com/FAt5VBo.png", null);
		sprite.setOnMouseClicked(e ->
		{
			sprite.requestFocus();
			/*
			List<Node> nodes = runTime.getRenderer().getChildren();
			for(int i = 0; i < nodes.size(); i++)
			{
				if(nodes.get(i) instanceof Line)
				{
					Line line = (Line)nodes.get(i);
					if(line.getStroke() != Color.LIGHTGREY)
					{
						runTime.getRenderer().getChildren().remove(line);
					}
				}
			}*/
			((Unit)sprite.getEntity()).moveDown();
		});
		Unit unit = new Unit(0, "testUnit", new GraphNode(3, 1), sprite, null, runTime.getEngine().getGraph());
		sprite.setEntity(unit);
		//unit end//
		runTime.getRenderer().drawEntity(block);
		runTime.getRenderer().drawEntity(unit);
	}
}