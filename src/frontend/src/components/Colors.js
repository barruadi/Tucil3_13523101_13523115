const colorPalette = [
    '#1890ff', '#13c2c2', '#52c41a', '#faad14',
    '#eb2f96', '#722ed1', '#2f54eb', '#fa541c'
];
  
const blockColors = {};
let colorIndex = 0;

export function getBlockColor(id) {
    if (!blockColors[id]) {
        blockColors[id] = colorPalette[colorIndex % colorPalette.length];
        colorIndex++;
    }
    return blockColors[id];
}
  