


export function formatDuring(unix, tpl) {
    const oneMinutes = 1000 * 60;
    const oneHours = oneMinutes * 60;
    const oneDay = oneHours * 24;

    if (tpl === 'd') {
        return Math.floor(unix / oneDay);
    } else if (tpl === 'h') {
        return Math.floor((unix % oneDay) / oneHours);
    } else if (tpl === 'm') {
        return Math.floor((unix % oneHours) / oneMinutes);
    }
}


