import React from 'react';
import './Board.css';
import { getBlockColor } from './Colors';

const CELL_SIZE = 60;

export default function Board({ blocks, boardSize, exit }) {
    return (
        <div
            className="board-container"
            style={{
                width: boardSize.width * CELL_SIZE,
                height: boardSize.height * CELL_SIZE
            }}
        >
            {blocks.map((block) => {
                const style = {
                    top: block.row * CELL_SIZE,
                    left: block.col * CELL_SIZE,
                    width: block.isVertical ? CELL_SIZE : block.size * CELL_SIZE,
                    height: block.isVertical ? block.size * CELL_SIZE : CELL_SIZE,
                    backgroundColor: getBlockColor(block.id),
                };

            return (
                <div className="block" key={block.id} style={style}>
                    {block.id}
                </div>
            );
            })}
            
            {exit && (
                <div
                    className="exit"
                    style={{
                        top: exit.row * CELL_SIZE,
                        left: exit.col * CELL_SIZE,
                        width: CELL_SIZE,
                        height: CELL_SIZE,
                    }}
                />
            )}
        </div>
    );
}
