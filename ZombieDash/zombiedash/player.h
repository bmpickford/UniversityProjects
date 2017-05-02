int setup_p(Sprite *sprite, byte *bitmap, Sprite *grenade, byte *g_bitmap);
void turn(Sprite *player, int dir);
bool play_turn(Sprite *player, bool has_grenade, Sprite *grenade);