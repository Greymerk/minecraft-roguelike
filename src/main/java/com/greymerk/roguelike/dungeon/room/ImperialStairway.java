package com.greymerk.roguelike.dungeon.room;

import java.util.List;
import java.util.stream.IntStream;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.fragment.parts.Pillar;
import com.greymerk.roguelike.dungeon.layout.Exit;
import com.greymerk.roguelike.dungeon.layout.ExitType;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.Candle;
import com.greymerk.roguelike.editor.blocks.IronBar;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.boundingbox.IBounded;

import net.minecraft.util.math.random.Random;

public class ImperialStairway extends AbstractRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Coord origin = this.getWorldPos().add(direction, Cell.SIZE * 2).freeze();
		Random rand = editor.getRandom(this.getWorldPos());
		
		this.clear(editor, rand, origin);
		this.walls(editor, rand, origin);
		this.ceiling(editor, rand, origin);
		this.upperLevel(editor, rand, origin);
		this.stairway(editor, rand, origin, Cardinal.reverse(direction));
		this.tunnels(editor, rand, origin.add(Cardinal.DOWN, 10).freeze(), Cardinal.reverse(direction));
		this.lowerRoom(editor, rand, origin.add(Cardinal.DOWN, 10).freeze());
		this.supports(editor, rand, origin.add(Cardinal.DOWN, 10).freeze());
		this.generateExits(editor, rand);
	}

	private void supports(IWorldEditor editor, Random rand, Coord origin) {
		CellSupport.generate(editor, rand, theme, origin);
		Cardinal.directions.forEach(dir -> {
			List.of(6, 12).forEach(i -> {
				CellSupport.generate(editor, rand, theme, origin.copy().add(dir, i));
				List.of(6, 12).forEach(j -> {
					CellSupport.generate(editor, rand, theme, origin.copy().add(dir, i).add(Cardinal.left(dir), j));
				});
			});
		});
	}

	private void lowerRoom(IWorldEditor editor, Random rand, Coord origin) {

		BoundingBox.of(origin).add(direction, 14).add(Cardinal.UP, 3)
			.grow(Cardinal.orthogonal(direction), 14).grow(Cardinal.UP, 5)
			.fill(editor, rand, theme.getPrimary().getWall());
		
		BoundingBox.of(origin).add(direction, 13).add(Cardinal.UP, 7)
			.grow(Cardinal.orthogonal(direction), 14).grow(Cardinal.UP)
			.fill(editor, rand, theme.getPrimary().getWall());
		
		BoundingBox.of(origin).add(direction, 12).add(Cardinal.UP, 8)
			.grow(Cardinal.orthogonal(direction), 14)
			.fill(editor, rand, theme.getPrimary().getWall());
		
		Cardinal.orthogonal(direction).forEach(o -> {
			BoundingBox.of(origin).add(Cardinal.reverse(direction), 2).add(o, 14).add(Cardinal.UP, 3)
				.grow(direction, 16).grow(Cardinal.UP, 5)
				.fill(editor, rand, theme.getPrimary().getWall());
			
			BoundingBox.of(origin).add(Cardinal.reverse(direction), 2).add(o, 13).add(Cardinal.UP, 7)
				.grow(direction, 16).grow(Cardinal.UP)
				.fill(editor, rand, theme.getPrimary().getWall());
			
			BoundingBox.of(origin).add(Cardinal.reverse(direction), 2).add(o, 12).add(Cardinal.UP, 8)
				.grow(direction, 16).fill(editor, rand, theme.getPrimary().getWall());
			
			List.of(-2, 2, 4, 8, 10).forEach(i -> {
				this.buttress(editor, rand, origin.add(direction, i).add(o, 10).freeze(), o);
			});
			
			List.of(2, 4, 8, 10).forEach(i -> {
				this.buttress(editor, rand, origin.add(direction, 10).add(o, i).freeze(), direction);
			});
			
			this.coveArch(editor, rand, origin.add(o, 10).freeze(), o);
			this.coveArch(editor, rand, origin.add(o, 10).add(direction, 6).freeze(), o);
			this.coveArch(editor, rand, origin.add(direction, 10).add(o, 6).freeze(), direction);
			
			this.inlay(editor, rand, origin.add(o, 10).add(direction, 3).freeze(), o);
			this.inlay(editor, rand, origin.add(o, 10).add(direction, 9).freeze(), o);
			this.inlay(editor, rand, origin.add(o, 3).add(direction, 10).freeze(), direction);
			this.inlay(editor, rand, origin.add(o, 9).add(direction, 10).freeze(), direction);
			
			//corners
			Pillar.generate(editor, rand, 
					origin.add(direction, 14).add(o, 14),
					theme.getPrimary(), 2, List.of(Cardinal.reverse(o), Cardinal.reverse(direction)));
			
			theme.getPrimary().getStair().setOrientation(o, true)
				.set(editor, rand, origin.add(direction, 14).add(o, 11).add(Cardinal.UP, 2));
			
			theme.getPrimary().getStair().setOrientation(direction, true)
				.set(editor, rand, origin.add(direction, 11).add(o, 14).add(Cardinal.UP, 2));
			
			theme.getPrimary().getWall()
				.set(editor, rand, origin.add(direction, 10).add(o, 10).add(Cardinal.UP, 8));
		});
		
		this.coveArch(editor, rand, origin.add(direction, 10).freeze(), direction);
	}

	private void inlay(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true)
			.set(editor, rand, origin.add(dir, 4).add(Cardinal.UP, 2));
		
		IntStream.range(0, 3).forEach(i -> {
			theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true)
				.set(editor, rand, origin.add(dir, 1 + i).add(Cardinal.UP, 8 - i));
		});
	}

	private void coveArch(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		Cardinal.orthogonal(dir).forEach(o -> {
			IntStream.range(0, 3).forEach(i -> {
				theme.getPrimary().getStair().setOrientation(Cardinal.reverse(o), true)
					.set(editor, rand, origin.add(dir, 1 + i).add(Cardinal.UP, 8 - i).add(o));
			});
			
			theme.getPrimary().getStair().setOrientation(Cardinal.reverse(o), true)
				.set(editor, rand, origin.add(dir, 4).add(Cardinal.UP, 2).add(o));
		});
	}

	private void buttress(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).add(dir, 4).grow(Cardinal.UP).fill(editor, rand, theme.getPrimary().getPillar());
		theme.getPrimary().getWall()
			.set(editor, rand, origin.add(dir, 4).add(Cardinal.UP, 2));
		theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true)
			.set(editor, rand, origin.add(dir, 3).add(Cardinal.UP, 2));
		
		BoundingBox.of(origin).add(dir, 3).add(Cardinal.UP, 3)
			.grow(Cardinal.UP, 2).fill(editor, rand, theme.getPrimary().getWall());
		
		IntStream.range(0, 3).forEach(i -> {
			Coord pos = origin.add(dir, i).add(Cardinal.UP, 8 - i);
			theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true)
				.set(editor, rand, pos);
			
			theme.getPrimary().getWall().set(editor, rand, pos.add(dir));
		});
	}

	private void tunnels(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		this.cell(editor, rand, origin.add(dir, 6).freeze(), List.of(
				Exit.of(ExitType.ALCOVE, origin.add(dir, 6), Cardinal.left(dir)),
				Exit.of(ExitType.ALCOVE, origin.add(dir, 6), Cardinal.right(dir)),
				Exit.of(ExitType.DOOR, origin.add(dir, 6), dir)
			));
		
		this.cell(editor, rand, origin.add(dir, 12).freeze(), List.of(
				Exit.of(ExitType.DOOR, origin.add(dir, 6), Cardinal.left(dir)),
				Exit.of(ExitType.DOOR, origin.add(dir, 6), Cardinal.right(dir))
			));
		
		Cardinal.orthogonal(dir).forEach(o -> {
			this.cell(editor, rand, origin.add(dir, 12).add(o, 6).freeze(), List.of(
					Exit.of(ExitType.DOOR, origin.add(dir, 12).add(o, 6), o),
					Exit.of(ExitType.WALL, origin.add(dir, 12).add(o, 6), Cardinal.reverse(dir))
				));
			
			this.cell(editor, rand, origin.add(dir, 12).add(o, 12).freeze(), List.of(
					Exit.of(ExitType.DOOR, origin.add(dir, 12).add(o, 12), Cardinal.reverse(dir))
				));
			
			this.cell(editor, rand, origin.add(dir, 6).add(o, 12).freeze(), List.of(
					Exit.of(ExitType.DOOR, origin.add(dir, 6).add(o, 12), Cardinal.reverse(dir)),
					Exit.of(ExitType.WALL, origin.add(dir, 6).add(o, 12), Cardinal.reverse(o))
				));
		});
		
	}

	private void cell(IWorldEditor editor, Random rand, Coord origin, List<Exit> exits) {
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.UP, 3).add(dir, 2)
				.grow(Cardinal.orthogonal(dir)).grow(Cardinal.left(dir))
				.fill(editor, rand, theme.getPrimary().getWall());
		});
		
		Cardinal.directions.forEach(dir -> {
			Pillar.generate(editor, rand, 
					origin.copy().add(dir, 2).add(Cardinal.left(dir), 2),
					theme, 2, List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
		});
		
		exits.forEach(exit -> {
			if(exit.type() == ExitType.DOOR) Fragment.generate(Fragment.ARCH, editor, rand, settings, origin, exit.dir());
			if(exit.type() == ExitType.ALCOVE) settings.getAlcove(rand).generate(editor, rand, settings, origin, exit.dir());
			if(exit.type() == ExitType.WALL) settings.getWallFragment(rand).generate(editor, rand, settings, origin, exit.dir());
		});
	}

	private void stairway(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).add(Cardinal.DOWN, 6).add(dir, 4)
			.grow(Cardinal.orthogonal(dir), 14).grow(dir, 10)
			.fill(editor, rand, theme.getPrimary().getWall());
		
		BoundingBox.of(origin).add(Cardinal.DOWN, 4).add(dir, 3)
			.grow(Cardinal.orthogonal(dir), 2).grow(Cardinal.DOWN).grow(dir, 7)
			.fill(editor, rand, theme.getPrimary().getWall());
		
		BoundingBox.of(origin).add(dir, 11).add(Cardinal.DOWN, 2)
			.grow(Cardinal.DOWN, 3).grow(dir, 3).grow(Cardinal.orthogonal(dir), 14)
			.fill(editor, rand, theme.getPrimary().getWall());
		
		BoundingBox.of(origin).add(dir, 10).add(Cardinal.DOWN, 2)
			.grow(Cardinal.orthogonal(dir), 9)
			.fill(editor, rand, theme.getPrimary().getWall());
		
		Cardinal.orthogonal(dir).forEach(o -> {
			BoundingBox.of(origin).add(Cardinal.DOWN, 10).add(dir, 3).add(o, 3)
				.grow(dir, 6).grow(o, 6).grow(Cardinal.UP, 3)
				.fill(editor, rand, theme.getPrimary().getWall());
			
			Pillar.generate(editor, rand, 
					origin.add(Cardinal.DOWN, 7).add(dir, 3).add(o, 2),
					theme, 1, List.of(Cardinal.reverse(o)));
			
			this.steps(editor, rand, origin.add(Cardinal.DOWN, 10).add(o, 6).freeze(), dir, 5);
			this.steps(editor, rand, origin.add(Cardinal.DOWN, 5).add(o, 4).add(dir, 6).freeze(), Cardinal.reverse(o), 2);
		});
		
		this.steps(editor, rand, origin.add(Cardinal.DOWN, 3).add(dir, 8).freeze(), dir, 3);
		
		Cardinal.orthogonal(dir).forEach(o -> {
			BoundingBox.of(origin).add(dir, 3).add(o, 11).add(Cardinal.DOWN, 5)
				.grow(dir, 7).grow(o, 3).grow(Cardinal.UP, 3)
				.fill(editor, rand, theme.getPrimary().getWall());
			
			this.archShelf(editor, rand, origin.add(Cardinal.DOWN, 5).add(dir, 6).add(o, 8).freeze(), o);
			this.archShelf(editor, rand, origin.add(Cardinal.DOWN, 5).add(dir, 8).add(o, 6).freeze(), dir);
			
			theme.getPrimary().getWall()
				.set(editor, rand, origin.add(o, 2).add(dir, 4).add(Cardinal.DOWN, 3));
			Candle.generate(editor, rand, origin.add(o, 2).add(dir, 4).add(Cardinal.DOWN, 2));
		});
		
		BoundingBox.of(origin).add(dir, 3).add(Cardinal.DOWN, 3)
			.grow(Cardinal.orthogonal(dir), 2)
			.fill(editor, rand, IronBar.get());
		
		Cardinal.orthogonal(dir).forEach(o -> {
			theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true)
				.set(editor, rand, origin.add(Cardinal.DOWN, 8).add(dir, 3).add(o, 2));
			
			theme.getPrimary().getWall()
				.set(editor, rand, origin.add(Cardinal.DOWN, 7).add(dir, 3).add(o, 2));
			
			theme.getPrimary().getStair().setOrientation(o, false)
				.set(editor, rand, origin.add(Cardinal.DOWN, 6).add(dir, 3).add(o, 3));
			
			BoundingBox.of(origin).add(Cardinal.DOWN, 6).add(dir, 3).add(o, 10)
				.grow(o, 4).fill(editor, rand, theme.getPrimary().getWall());
			
			BoundingBox.of(origin).add(Cardinal.DOWN, 5).add(dir, 9).add(o, 9)
				.grow(dir).grow(o).grow(Cardinal.UP, 4).fill(editor, rand, theme.getPrimary().getWall());
		});
		
	}

	private void archShelf(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		Cardinal.orthogonal(dir).forEach(o -> {
			Pillar.generate(editor, rand, origin.add(dir, 2).add(o, 2), theme, 2, List.of(Cardinal.reverse(o)));
		});
		
		BoundingBox.of(origin).add(dir, 2).add(Cardinal.UP, 3)
			.grow(Cardinal.orthogonal(dir), 2)
			.fill(editor, rand, theme.getPrimary().getWall());
		
		this.settings.getWallFragment(rand).generate(editor, rand, settings, origin, dir);
	}

	private void steps(IWorldEditor editor, Random rand, Coord origin, Cardinal dir, int count) {
		IntStream.range(0, count).forEach(i -> {
			theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), false)
				.fill(editor, rand, 
					BoundingBox.of(origin).add(dir, i).add(Cardinal.UP, i).grow(Cardinal.orthogonal(dir)));
			
			Cardinal.orthogonal(dir).forEach(o -> {
				theme.getPrimary().getWall().set(editor, rand, origin.add(dir, i).add(Cardinal.UP, i).add(o, 2));
				Candle.generate(editor, rand, origin.add(dir, i).add(Cardinal.UP, i + 1).add(o, 2));
			});
			
			if(i > 0) {
				BoundingBox.of(origin).add(dir, i)
					.grow(Cardinal.UP, i - 1).grow(Cardinal.orthogonal(dir), 2)
					.fill(editor, rand, theme.getPrimary().getWall());
			}
		});
		
	}

	private void upperLevel(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 10).add(Cardinal.DOWN)
				.grow(dir, 4).grow(Cardinal.orthogonal(dir), 14)
				.fill(editor, rand, theme.getPrimary().getWall());
			
			List.of(10, 14).forEach(i -> {
				BoundingBox.of(origin).add(dir, i).add(Cardinal.UP, 3)
					.grow(Cardinal.orthogonal(dir), 14)
					.fill(editor, rand, theme.getPrimary().getWall());				
			});
			
			Pillar.generate(editor, rand, 
					origin.add(dir, 14).add(Cardinal.left(dir), 14) 
					,theme, 2, 
					List.of(Cardinal.reverse(dir), Cardinal.right(dir)));
			
			Cardinal.orthogonal(dir).forEach(o -> {
				List.of(3, 9).forEach(i -> {
					Cardinal.orthogonal(dir).forEach(orth -> {
						Pillar.generate(editor, rand, 
							origin.add(dir, 14).add(o, i).add(orth),
							theme, 2,
							List.of(Cardinal.reverse(dir), orth));

						Pillar.generate(editor, rand, 
								origin.add(dir, 10).add(o, i).add(orth),
								theme, 2,
								List.of(dir, Cardinal.reverse(dir), orth));
						
						BoundingBox.of(origin).add(dir, 9).add(Cardinal.UP, 3)
							.add(o, i).add(orth).grow(dir, 4)
							.fill(editor, rand, theme.getPrimary().getWall());
					});
				});
				
				List.of(2, 4).forEach(i -> {
					theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true)
						.set(editor, rand, origin.add(Cardinal.UP, 3).add(dir, 8).add(o, i));
				});
			});
		});
		
		List.of(direction, Cardinal.left(direction), Cardinal.right(direction)).forEach(dir -> {
			BoundingBox.of(origin).add(dir, 10).grow(Cardinal.orthogonal(dir), 10).fill(editor, rand, IronBar.get(), Fill.AIR.and(Fill.SUPPORTED));
		});
		
		Cardinal.orthogonal(direction).forEach(o -> {
			BoundingBox.of(origin).add(Cardinal.reverse(direction), 10).add(o, 3).grow(o, 6).fill(editor, rand, IronBar.get(), Fill.AIR.and(Fill.SUPPORTED));
		});
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			List.of(2, 4, 8, 10, 14).forEach(i -> {
				BoundingBox.of(origin).add(Cardinal.UP, 4).add(dir, i)
					.grow(Cardinal.orthogonal(dir), 14)
					.fill(editor, rand, theme.getPrimary().getWall());
			});				
		});
		
		BoundingBox.of(origin).add(Cardinal.UP, 5)
			.grow(Cardinal.directions, 14)
			.fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
	}

	private void walls(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 15)
				.grow(Cardinal.orthogonal(dir), 15)
				.grow(Cardinal.UP, 3).grow(Cardinal.DOWN, 11)
				.fill(editor, rand, theme.getPrimary().getWall());
		});
		
		BoundingBox.of(origin).add(Cardinal.DOWN, 11)
			.grow(Cardinal.directions, 14)
			.fill(editor, rand, theme.getPrimary().getFloor());
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin)
			.grow(Cardinal.directions, 14)
			.grow(Cardinal.UP, 4).grow(Cardinal.DOWN, 10)
			.fill(editor, rand, Air.get());
	}

	@Override
	public IBounded getBoundingBox(Coord origin, Cardinal dir) {
		return BoundingBox.of(origin).add(dir, Cell.SIZE * 2)
				.grow(Cardinal.UP, 6).grow(Cardinal.DOWN, 12)
				.grow(Cardinal.directions, 15);
	}
	
	@Override
	public String getName() {
		return Room.IMPERIAL_STAIRWAY.name();
	}
	
	@Override
	public CellManager getCells(Cardinal dir) {
		CellManager cells = new CellManager();
		
		BoundingBox.of(Coord.ZERO).add(dir, 2)
			.grow(Cardinal.directions, 2)
			.grow(Cardinal.DOWN)
			.forEach(pos -> {
				cells.add(Cell.of(pos, CellState.OBSTRUCTED, this));
			});;
		
		Cardinal.directions.forEach(d -> {
			BoundingBox.of(Coord.ZERO).add(dir, 2).add(d, 3)
				.grow(Cardinal.orthogonal(d), 2).grow(Cardinal.DOWN)
				.forEach(pos -> {
					cells.add(Cell.of(pos, CellState.POTENTIAL, this));
				});
		});
			
		return cells;
	}

}
