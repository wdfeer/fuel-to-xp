package org.wdfeer.fuel_to_xp.util

import kotlin.random.Random

fun Float.randomRound(): Int = this.toInt() + if (Random.nextFloat() < this) 1 else 0