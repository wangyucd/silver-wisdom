export function formatDateTime(value?: string) {
  if (!value) {
    return '--';
  }
  return value.replace('T', ' ').slice(0, 16);
}

export function buildTags(tags: string[]) {
  return tags.filter(Boolean).join(' / ') || '暂无标签';
}
