package com.greymerk.roguelike.dungeon.room;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.greymerk.roguelike.dungeon.cell.Cell;
import com.greymerk.roguelike.dungeon.cell.CellManager;
import com.greymerk.roguelike.dungeon.cell.CellState;
import com.greymerk.roguelike.dungeon.fragment.Fragment;
import com.greymerk.roguelike.dungeon.fragment.parts.CellSupport;
import com.greymerk.roguelike.dungeon.layout.ExitType;
import com.greymerk.roguelike.editor.Cardinal;
import com.greymerk.roguelike.editor.Coord;
import com.greymerk.roguelike.editor.Fill;
import com.greymerk.roguelike.editor.IBlockFactory;
import com.greymerk.roguelike.editor.IWorldEditor;
import com.greymerk.roguelike.editor.blocks.Air;
import com.greymerk.roguelike.editor.blocks.IronBar;
import com.greymerk.roguelike.editor.blocks.Lantern;
import com.greymerk.roguelike.editor.blocks.stair.IStair;
import com.greymerk.roguelike.editor.boundingbox.BoundingBox;
import com.greymerk.roguelike.editor.shapes.Shape;

import net.minecraft.util.math.random.Random;

public class PanopticonRoom extends AbstractLargeRoom implements IRoom {

	@Override
	public void generate(IWorldEditor editor) {
		Random rand = editor.getRandom(this.getWorldPos());
		Coord origin = this.worldPos.copy().add(direction, Cell.SIZE * 2).freeze();
		
		clear(editor, rand, origin.copy().freeze());
		doors(editor, rand, origin.copy().freeze());
		tower(editor, rand, origin.copy().freeze());
		ceiling(editor, rand, origin.copy().freeze());
		pillars(editor, rand, origin.copy().freeze());
		lowerPlatforms(editor, rand, origin.copy().add(Cardinal.DOWN, 7).freeze());
		middlePlatforms(editor, rand, origin.copy().add(Cardinal.DOWN, 3).freeze());
		upperPlatforms(editor, rand, origin.copy().add(Cardinal.UP).freeze());
		bridges(editor, rand, origin.copy().freeze());
		bars(editor, rand, origin.copy().freeze());
		decorations(editor, rand, origin.copy().freeze());
		supports(editor, rand, origin.copy().add(Cardinal.DOWN, 10).freeze());
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

	private void decorations(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			if(this.getExitType(dir) != ExitType.DOOR) {
				deco(editor, rand, origin.copy().add(Cardinal.UP, 2).add(dir, 12), dir);
				deco(editor, rand, origin.copy().add(Cardinal.DOWN, 2).add(dir, 12), dir);
			}
			deco(editor, rand, origin.copy().add(Cardinal.DOWN, 6).add(dir, 12), dir);
			deco(editor, rand, origin.copy().add(Cardinal.DOWN, 10).add(dir, 12), dir);
			Cardinal.orthogonal(dir).forEach(o -> {
				List.of(6, 12).forEach(i -> {
					deco(editor, rand, origin.copy().add(Cardinal.UP, 2).add(dir, 12).add(o, i), dir);
					deco(editor, rand, origin.copy().add(Cardinal.DOWN, 2).add(dir, 12).add(o, i), dir);
					List.of(6, 10).forEach(j -> {
						deco(editor, rand, origin.copy().add(Cardinal.DOWN, j).add(dir, 12).add(o, i), dir);
					});
				});
			});
		});
	}

	
	private void deco(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		Cardinal.orthogonal(dir).forEach(o -> {
			theme.getPrimary().getStair().setOrientation(Cardinal.reverse(o), true)
				.set(editor, rand, origin.copy().add(dir, 2).add(o).add(Cardinal.UP, 2));
		});
		this.settings.getWallFragment(rand).generate(editor, rand, settings, origin, dir);
	}

	private void bars(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			barWall(editor, rand, origin.copy().add(Cardinal.DOWN, 10).add(dir, 10), dir);
			barWall(editor, rand, origin.copy().add(Cardinal.DOWN, 6).add(dir, 10), dir);
			if(this.getExitType(dir) != ExitType.DOOR) {
				barWall(editor, rand, origin.copy().add(Cardinal.DOWN, 2).add(dir, 10), dir);
			}
			Cardinal.orthogonal(dir).forEach(o -> {
				barWall(editor, rand, origin.copy().add(Cardinal.DOWN, 10).add(dir, 10).add(o, 6), dir);
				barWall(editor, rand, origin.copy().add(Cardinal.DOWN, 6).add(dir, 10).add(o, 6), dir);
				barWall(editor, rand, origin.copy().add(Cardinal.DOWN, 2).add(dir, 10).add(o, 6), dir);
				List.of(2, 4, 8, 10).forEach(i -> {
					barWall(editor, rand, origin.copy().add(Cardinal.DOWN, 10).add(dir, 12).add(o, i), o);
					barWall(editor, rand, origin.copy().add(Cardinal.DOWN, 6).add(dir, 12).add(o, i), o);
				});
				if(this.getExitType(dir) == ExitType.DOOR) {
					List.of(8, 10).forEach(i -> {
						barWall(editor, rand, origin.copy().add(Cardinal.DOWN, 2).add(dir, 12).add(o, i), o);
					});
				} else {
					List.of(2, 4, 8, 10).forEach(i -> {
						barWall(editor, rand, origin.copy().add(Cardinal.DOWN, 2).add(dir, 12).add(o, i), o);
					});
				}
			});
		});
	}
	
	private void barWall(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		BoundingBox.of(origin).grow(Cardinal.orthogonal(dir)).grow(Cardinal.UP, 2).fill(editor, rand, IronBar.getBroken(), Fill.AIR);
	}

	private void bridges(IWorldEditor editor, Random rand, Coord origin) {
		IStair stair = theme.getPrimary().getStair();
		this.getEntrancesFromType(ExitType.DOOR).forEach(dir -> {
			BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 3).grow(Cardinal.orthogonal(dir)).grow(dir, 13).fill(editor, rand, theme.getPrimary().getFloor());
			BoundingBox.of(origin).add(Cardinal.DOWN, 2).add(dir, 8).grow(Cardinal.orthogonal(dir)).grow(dir, 6).fill(editor, rand, theme.getPrimary().getWall());
			Cardinal.orthogonal(dir).forEach(o -> {
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(Cardinal.DOWN, 2).add(dir, 7).add(o));				
				stair.setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(Cardinal.DOWN, 3).add(dir, 8).add(o));
				
				BoundingBox.of(origin).add(dir, 12).add(o, 3).grow(Cardinal.orthogonal(o)).grow(Cardinal.DOWN, 2).grow(Cardinal.UP, 5).fill(editor, rand, theme.getPrimary().getWall());
				BoundingBox.of(origin).add(Cardinal.DOWN).add(dir, 12).add(o, 2).grow(Cardinal.orthogonal(o)).grow(Cardinal.DOWN).fill(editor, rand, theme.getPrimary().getWall());
				
				BoundingBox.of(origin).add(dir, 2).add(o).grow(dir, 8).fill(editor, rand, IronBar.get(), Fill.SUPPORTED);
			});
		});
		
		Cardinal.directions.forEach(dir -> {
			if(this.getExitType(dir) != ExitType.DOOR) {
				BoundingBox.of(origin).add(Cardinal.UP).add(dir, 10).grow(Cardinal.orthogonal(dir), 3).fill(editor, rand, theme.getPrimary().getWall());
				Cardinal.orthogonal(dir).forEach(o -> {
					BoundingBox.of(origin).add(Cardinal.UP).add(dir, 11).add(o, 2).grow(dir, 2).fill(editor, rand, theme.getPrimary().getWall());
				});
				BoundingBox.of(origin).add(Cardinal.UP).add(dir, 11).grow(Cardinal.orthogonal(dir), 3).grow(dir, 3).fill(editor, rand, theme.getPrimary().getFloor());
			}
		});
	}

	private Iterable<Cardinal> getEntrancesFromType(ExitType door) {
		Set<Cardinal> dirs = new HashSet<Cardinal>();
		this.exits.forEach(exit -> {
			if(exit.type() == ExitType.DOOR) dirs.add(exit.dir());
		});
		dirs.add(Cardinal.reverse(direction));
		return dirs;
	}

	private void upperPlatforms(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			Cardinal.orthogonal(dir).forEach(o -> {
				BoundingBox.of(origin).add(dir, 10).add(o, 5).grow(o, 2).fill(editor, rand, theme.getPrimary().getWall());
				BoundingBox.of(origin).add(dir, 11).add(o, 4).grow(dir, 2).fill(editor, rand, theme.getPrimary().getWall());
				BoundingBox.of(origin).add(dir, 14).add(o, 5).grow(o, 8).fill(editor, rand, theme.getPrimary().getWall());
				BoundingBox.of(origin).add(dir, 12).add(o, 5).grow(o, 8).grow(Cardinal.orthogonal(o)).fill(editor, rand, theme.getPrimary().getFloor(), Fill.AIR);
			});
		});
	}

	private void middlePlatforms(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 9).grow(Cardinal.orthogonal(dir), 14).grow(dir).fill(editor, rand, theme.getPrimary().getWall());
			BoundingBox.of(origin).add(dir, 14).grow(Cardinal.orthogonal(dir), 14).fill(editor, rand, theme.getPrimary().getWall());
			
			Cardinal.orthogonal(dir).forEach(o -> {
				BoundingBox.of(origin).add(dir, 11).add(o, 3).grow(Cardinal.orthogonal(dir)).grow(dir, 2).fill(editor, rand, theme.getPrimary().getWall());
				BoundingBox.of(origin).add(dir, 11).add(o, 9).grow(Cardinal.orthogonal(dir)).grow(dir, 2).fill(editor, rand, theme.getPrimary().getWall());
				theme.getPrimary().getWall().set(editor, rand, origin.copy().add(dir, 8).add(o, 3));
			});
			BoundingBox.of(origin).add(dir, 11).grow(dir, 3).grow(Cardinal.left(dir), 13).grow(Cardinal.right(dir), 7).fill(editor, rand, theme.getPrimary().getFloor(), Fill.AIR);
		});
		
	}

	private void lowerPlatforms(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 8).grow(dir, 3).grow(Cardinal.orthogonal(dir), 14).fill(editor, rand, theme.getPrimary().getWall());
			BoundingBox.of(origin).add(dir, 14).grow(Cardinal.orthogonal(dir), 14).fill(editor, rand, theme.getPrimary().getWall());
			Cardinal.orthogonal(dir).forEach(o -> {
				BoundingBox.of(origin).add(dir, 11).add(o, 3).grow(Cardinal.orthogonal(dir)).grow(dir, 2).fill(editor, rand, theme.getPrimary().getWall());
				BoundingBox.of(origin).add(dir, 11).add(o, 9).grow(Cardinal.orthogonal(dir)).grow(dir, 2).fill(editor, rand, theme.getPrimary().getWall());
			});
			BoundingBox.of(origin).add(dir, 11).grow(dir, 3).grow(Cardinal.left(dir), 13).grow(Cardinal.right(dir), 7).fill(editor, rand, theme.getPrimary().getFloor(), Fill.AIR);
		});
	}

	private void pillars(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			cornerPillarSet(editor, rand, origin.copy().add(Cardinal.DOWN, 10).add(dir, 7).add(Cardinal.left(dir), 7).freeze(),
					List.of(dir, Cardinal.left(dir)));
			Cardinal.orthogonal(dir).forEach(o -> {
				mainPillarSet(editor, rand, origin.copy().add(dir, 8).add(Cardinal.DOWN, 10).add(o, 3), dir);
				
			});
		});
	}

	private void cornerPillarSet(IWorldEditor editor, Random rand, Coord origin, List<Cardinal> corner) {
		pillar(editor, rand, origin.add(corner.getFirst()).add(corner.getLast()),
				List.of(Cardinal.reverse(corner.getFirst()), Cardinal.reverse(corner.getLast())), 2);
		pillar(editor, rand, origin.add(corner.getFirst(), 2).add(corner.getLast(), 2),
				List.of(Cardinal.reverse(corner.getFirst()), Cardinal.reverse(corner.getLast())), 6);
		pillar(editor, rand, origin.add(corner.getFirst(), 2).add(corner.getLast(), 2).add(Cardinal.UP, 8),
				List.of(Cardinal.reverse(corner.getFirst()), Cardinal.reverse(corner.getLast())), 6);
		pillar(editor, rand, origin.add(corner.getFirst(), 3).add(corner.getLast(), 3),
				List.of(corner.getFirst(), corner.getLast()), 14);
		pillar(editor, rand, origin.add(corner.getFirst(), 7).add(corner.getLast(), 7),
				List.of(Cardinal.reverse(corner.getFirst()), Cardinal.reverse(corner.getLast())), 14);
		corner.forEach(dir -> {
			Cardinal o = dir == corner.getFirst() ? corner.getLast() : corner.getFirst();
			pillar(editor, rand, origin.add(dir, 3).add(o),
					List.of(Cardinal.reverse(o), dir), 14);
			pillar(editor, rand, origin.add(dir, 7).add(o),
					List.of(Cardinal.reverse(o), Cardinal.reverse(dir)), 14);
			pillar(editor, rand, origin.add(dir, 7).add(o, 3),
					List.of(o, Cardinal.reverse(dir)), 14);
		});
	}

	private void mainPillarSet(IWorldEditor editor, Random rand, Coord origin, Cardinal dir) {
		pillar(editor, rand, origin.copy().add(dir), Cardinal.directions, 2);
		pillar(editor, rand, origin.copy().add(dir).add(Cardinal.UP, 4), Cardinal.directions, 2);
		
		Cardinal.orthogonal(dir).forEach(o -> {
			pillar(editor, rand, origin.copy().add(dir, 2).add(o), List.of(Cardinal.reverse(dir), dir, o), 14);
			pillar(editor, rand, origin.copy().add(dir, 6).add(o), Cardinal.directions, 14);
		});
		theme.getPrimary().getStair().setOrientation(Cardinal.reverse(dir), true).set(editor, rand, origin.copy().add(dir, 2).add(Cardinal.UP, 14));
	}
	
	

	private void pillar(IWorldEditor editor, Random rand, Coord origin, List<Cardinal> directions, int height) {
		BoundingBox.of(origin).grow(Cardinal.UP, height).fill(editor, rand, theme.getPrimary().getPillar());
		directions.forEach(dir -> {
			theme.getPrimary().getStair().setOrientation(dir, true).set(editor, rand, origin.copy().add(dir).add(Cardinal.UP, height));
		});
	}

	private void ceiling(IWorldEditor editor, Random rand, Coord origin) {
		Cardinal.directions.forEach(dir -> {
			List.of(2, 4, 8, 10, 14).forEach(step -> {
				BoundingBox.of(origin).add(Cardinal.UP, 5).add(dir, step).grow(Cardinal.orthogonal(dir), 14).fill(editor, rand, theme.getPrimary().getWall());	
			});
		});
	}

	private void tower(IWorldEditor editor, Random rand, Coord origin) {
		IBlockFactory walls = theme.getPrimary().getWall();
		IStair stair = theme.getPrimary().getStair();
		
		BoundingBox.of(origin).add(Cardinal.DOWN).grow(Cardinal.directions).fill(editor, rand, theme.getPrimary().getFloor());
		
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.left(dir), 2).grow(Cardinal.DOWN, 10).grow(Cardinal.UP, 5).fill(editor, rand, theme.getPrimary().getPillar());
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.UP, 3).grow(Cardinal.orthogonal(dir)).grow(Cardinal.UP, 2).fill(editor, rand, walls);
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.DOWN).grow(Cardinal.orthogonal(dir)).grow(Cardinal.DOWN).fill(editor, rand, walls);
			Lantern.set(editor, origin.copy().add(dir, 2).add(Cardinal.DOWN, 3));
			BoundingBox.of(origin).add(dir, 2).add(Cardinal.DOWN, 6).grow(Cardinal.orthogonal(dir)).fill(editor, rand, walls);
			
			Cardinal.orthogonal(dir).forEach(o -> {
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 2).add(o).add(Cardinal.UP, 2));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 2).add(o).add(Cardinal.DOWN, 3));
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 2).add(o).add(Cardinal.DOWN, 7));
				
				stair.setOrientation(Cardinal.reverse(o), true).set(editor, rand, origin.copy().add(dir, 3).add(o).add(Cardinal.UP, 4));
				stair.setOrientation(dir, true).set(editor, rand, origin.copy().add(dir, 4).add(o, 2).add(Cardinal.UP, 4));
				stair.setOrientation(dir, true).set(editor, rand, origin.copy().add(dir, 3).add(o, 2).add(Cardinal.UP, 2));
				
				BoundingBox.of(origin).add(dir, 3).add(o, 2).add(Cardinal.UP, 3).grow(Cardinal.UP)
					.getShape(Shape.RECTSOLID).fill(editor, rand, walls);
			});
				
			if(this.getExitType(dir) != ExitType.DOOR) {
				BoundingBox.of(origin).add(dir, 2).grow(Cardinal.orthogonal(dir)).fill(editor, rand, IronBar.getBroken());
			}
		});
	}

	private void doors(IWorldEditor editor, Random rand, Coord origin) {
		this.getEntrancesFromType(ExitType.DOOR).forEach(dir -> {
			Fragment.generate(Fragment.ARCH, editor, rand, settings, origin.copy().add(dir, 12), dir);
		});
	}

	private void clear(IWorldEditor editor, Random rand, Coord origin) {
		BoundingBox.of(origin).grow(Cardinal.directions, 14).grow(Cardinal.DOWN, 10).grow(Cardinal.UP, 5).fill(editor, rand, Air.get());
		BoundingBox.of(origin).add(Cardinal.DOWN, 11).grow(Cardinal.directions, 15).fill(editor, rand, theme.getPrimary().getFloor());
		BoundingBox.of(origin).add(Cardinal.UP, 6).grow(Cardinal.directions, 15).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		Cardinal.directions.forEach(dir -> {
			BoundingBox.of(origin).add(dir, 15).grow(Cardinal.orthogonal(dir), 15).grow(Cardinal.DOWN, 11).grow(Cardinal.UP, 6).fill(editor, rand, theme.getPrimary().getWall(), Fill.SOLID);
		});
	}

	@Override
	public CellManager getCells(Cardinal dir) {
		
		CellManager cells = super.getCells(dir);
		
		Coord origin = Coord.ZERO.add(Cardinal.DOWN);
		BoundingBox bb = BoundingBox.of(origin);
		bb.add(dir, 2).grow(Cardinal.directions);
		bb.getShape(Shape.RECTSOLID).get().forEach(pos -> {
			cells.add(Cell.of(pos, CellState.OBSTRUCTED, this));
		});
		
		for(Cardinal d : Cardinal.directions) {
			cells.add(Cell.of(origin.copy().add(dir, 2).add(d, 2), CellState.OBSTRUCTED, this).addWall(d));
			cells.add(Cell.of(origin.copy().add(dir, 2).add(d, 2).add(Cardinal.left(d), 2), CellState.OBSTRUCTED, this).addWall(d).addWall(Cardinal.left(d)));
			for(Cardinal o : Cardinal.orthogonal(d)) {
				cells.add(Cell.of(origin.copy().add(dir, 2).add(d, 2).add(o), CellState.OBSTRUCTED, this).addWall(d));
			}
		}
		
		
		return cells;
	}
	
	@Override
	public BoundingBox getBoundingBox(Coord origin, Cardinal dir) {
		return super.getBoundingBox(origin, dir).grow(Cardinal.DOWN, 10);
	}

	@Override
	public String getName() {
		return Room.PANOPTICON.name();
	}
	
}
